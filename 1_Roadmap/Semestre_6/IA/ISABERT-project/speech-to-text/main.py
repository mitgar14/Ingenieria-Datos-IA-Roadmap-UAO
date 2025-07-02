import os
import json
import logging

from flask import Flask, jsonify, request
from utils import download_file_from_gcs, upload_to_gcs, transcribe_audio, format_to_json, cleanup_temp_files
from assistant import AssistantConnection

logging.getLogger().setLevel(logging.INFO)

app = Flask(__name__)

@app.route('/process-call', methods=['POST'])
def process_call():
    """
    Perform the transcription, analysis, and formatting of a single call
    Args:
        request:
            JSON payload with the following structure:
            {
                "bucket_name": "five9_api",
                "source_blob_name": "recordings/las-madrinas/recording-000432C67AB5413B8989129A1D18ADA1-579393.wav",
                "destination_file_name": "/tmp/test_audio.wav",
                "destination_bucket": "madrinas-bucket",
                "destination_blob_name": "speech-to-text"
                "training": True or False
            }
    Returns:
        JSON response with the following structure:
        {
            "call_id": "unique-id",
            "client_name": "client-name",
            "call_duration": "00:15:00,
            "data": {
                "client": "client-message",
                "client_sentiment": 1 to 5,
                "agent_name": "agent-name",
                "agent": "agent-message",
                "agent_sentiment": 1 to 5,
                }
        }

    """
    request_json = request.get_json()

    if not request_json:
        return jsonify({"error": "Missing JSON payload"}), 400

    bucket_name = request_json.get('bucket_name')
    source_blob_name = request_json.get('source_blob_name')
    destination_bucket = request_json.get('destination_bucket')
    destination_blob_name = request_json.get('destination_blob_name')

    training = request_json.get('training', False)

    if not all([bucket_name, source_blob_name, destination_bucket, destination_blob_name]):
        return jsonify({"error": "Missing required parameters"}), 400

    # Temporary path to save the audio file locally
    # This is made because Whisper requires the file in memory to perform the transcription
    destination_file_name = "/tmp/test_audio.wav"

    try:
        # Download the audio file from GCS
        download_file_from_gcs(bucket_name, source_blob_name, destination_file_name)
        logging.info(f"===================== The audio has been succesfully downloaded here: {destination_file_name} =====================")
    except Exception as e:
        logging.error(f"Error while dowloading the file: {source_blob_name}: {e}")
        return jsonify({"error": f"Error while dowloading the file:: {e}"}), 500

    try:
        # Perform the transcription using Whisper
        transcribed_text = transcribe_audio(destination_file_name)
        if transcribed_text == False:
            raise Exception("The transcription failed")
        logging.info("===================== Transciption completed ==============================")

    except Exception as e:
        logging.error(f"Error perfoming the transcription {source_blob_name}: {e}")
        return jsonify({"error": f"Error perfoming the transcription: {e}"}), 500

    try:
        # Send the transcription to the GPT assistant
        assistant = AssistantConnection()
        response = assistant.send_message(transcribed_text, training)
        logging.info("===================== Assistant message received =====================")
        logging.info (response)

    except Exception as e:
        logging.error(f"Error with the assistant {source_blob_name}: {e}")
        return jsonify({"error": f"Error with the assistant: {e}"}), 500

    try:
        # Format the response and upload it to GCS as a JSON file
        call_id = source_blob_name.split('/')[-1].split('.')[0]  # Extract call_id from the audio file name

        client_name = "Las Madrinas"
        call_duration = "00:15:00"  #TODO: Is there a way to get the call duration?

        json_data = format_to_json(call_id, client_name, call_duration, response)
        upload_to_gcs(destination_bucket, destination_blob_name, json.dumps(json_data))
        logging.info(f" ===================== Data uploaded to GCS for the file {destination_blob_name} =====================")

        # Clean up temporary files
        cleanup_temp_files()
        logging.info(" ===================== The directory /tmp has been cleaned up =====================")

        return jsonify(json_data), 200

    except Exception as e:
        logging.error(f"Error al subir los datos a GCS para el archivo {source_blob_name}: {e}")
        return jsonify({"error": f"Error uploading data to GCS: {e}"}), 500
 
if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080)