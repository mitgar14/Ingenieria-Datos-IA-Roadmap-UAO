import json
import whisper
import logging
import os

from openai import OpenAI
from google.cloud import storage
from datetime import datetime


def download_file_from_gcs(bucket_name, source_blob_name, destination_file_name) -> bool:
    """
    Download audio from Google Cloud Storage
    Args:
        bucket_name (str): Name of the bucket
        source_blob_name (str): Name of the audio file in the bucket
        destination_file_name (str): Name of the file to save the audio
    Returns:
        bool: True if the file was downloaded successfully, False otherwise
    """
    try:
        storage_client = storage.Client()
        bucket = storage_client.bucket(bucket_name)
        blob = bucket.blob(source_blob_name)
        blob.download_to_filename(destination_file_name)
        return True
    except Exception as e:
        logging.error(f"Error downloading file: {e}")
        return False
    

def transcribe_audio(file_path, language="es") -> str:
    """ 
    Transcribe the audio file using Whisper
    Args: 
        file_path (str): Path to the audio file
        language (str): Language of the audio file
    Returns:
        str: Transcribed text if the transcription was successful, False otherwise
    """
    try:
        model = whisper.load_model("small")  # Model could be ("small", "medium", "large")
        result = model.transcribe(file_path, language=language)
        return result['text']
    except Exception as e:
        logging.error(f"Error transcribing audio: {e}")
        return False


def format_to_json(call_id, client_name, call_duration, gpt_response) -> dict: 
    """
    Format the GPT response into a JSON object
    Args: 
        call_id (str): Call ID
        client_name (str): Name of the client
        call_duration (int): Duration of the call
        gpt_response (str): GPT response in JSON format
    Returns:
        dict: JSON object with the formatted data if successful, False otherwise
    """
    try:
        client_texts = []  
        agent_texts = []  
        
        # Since the GPT response is already in JSON format, we can parse it directly
        result = json.loads(gpt_response)

        agent_texts = result.get('agent', [])
        agent_name = result.get('agent_name', '')
        client_texts = result.get('client', [])

        # Create a structure for the response

        json_response = {
            "call_id": call_id,
            "transcription_date": str(datetime.now().date()),
            "client": client_name,
            "call_duration": call_duration, 
            "data": {
                "agent_name": agent_name,
                "agent": agent_texts,
                "client": client_texts,
                } 
            }

        # If the client_sentiment and agent_sentiment are present in the GPT's response,
        # It implies that we are using the training route.
        # It's important to keep in mind that the sentiment values here are perform by the agent!!
        if 'client_sentiment' in result and 'agent_sentiment' in result:
            client_sentiment = result.get('client_sentiment')
            agent_sentiment = result.get('agent_sentiment')

            # Add the sentiment to the JSON response
            json_response['data']['agent_sentiment'] = agent_sentiment
            json_response['data']['client_sentiment'] = client_sentiment

        return json_response

    except Exception as e:
        logging.error(f"Error formatting to JSON: {e}")
        return False


def upload_to_gcs(bucket_name, destination_blob_name, data) -> bool:
    """
    Upload the transcription to Google Cloud Storage
    Args:
        bucket_name (str): Name of the bucket
        destination_blob_name (str): Name of the file in the bucket
        data (str): Data to upload
    Returns:
        bool: True if the upload was successful, False otherwise
    """
    try:
        storage_client = storage.Client()
        bucket = storage_client.bucket(bucket_name)
        blob = bucket.blob(destination_blob_name)
        blob.upload_from_string(data, content_type='application/json')
        return True
    except Exception as e:
        logging.error(f"Error uploading to GCS: {e}")
        return False
    
def cleanup_temp_files():
    """
    Clean up temporary files in the /tmp directory
    """
    temp_dir = '/tmp'
    for f in os.listdir(temp_dir):
        file_path = os.path.join(temp_dir, f)
        try:
            if os.path.isfile(file_path) or os.path.islink(file_path):
                os.unlink(file_path)
        except Exception as e:
            print(f'Error deleting file {file_path}: {e}')