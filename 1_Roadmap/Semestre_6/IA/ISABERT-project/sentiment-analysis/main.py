import logging
import os 

from flask import Flask, request, jsonify
from utils import process_sentiment, upload_to_gcs

app = Flask(__name__)

# Configuración de logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s - %(filename)s:%(lineno)d'
)

@app.route('/perform-sentiment-analysis', methods=['POST'])
def analyze_call():

    """
    Expected JSON payload:
        {
        "call_id": "unique-id",
        "client_name": "client-name",
        "call_duration": "00:15:00",
        "data": {
                "agent_name": "agent_name",
                "agent": "agent-message",
                "client": "client-message"
            }
        }
    Returns:
        {
        "call_id": "unique-id",
        "client_name": "client-name",
        "call_duration": "00:15:00",
        "data": {
                "agent_name": "agent_name",
                "agent": "agent-message",
                "agent_sentiment": 5,
                "client": "client-message",
                "client_sentiment": 1
            }
        }

    """

    if request.get_json() is None:
        return jsonify({"error": "Request must be a JSON payload"}), 400
    
    elif request.get_json().get("data") is None:
        return jsonify({"error": "Request must contain 'data' key"}), 400
    
    elif request.get_json()["data"].get("client") is None or request.get_json()["data"].get("agent") is None:
        return jsonify({"error": "Request must contain 'client' and 'agent' keys inside a data object"}), 400

    try:
        data = request.get_json()

        call_id = data.get("call_id")
        client_name = data.get("client_name")
        call_duration = data.get("call_duration")
        client_message = data["data"].get("client")
        agent_message = data["data"].get("agent")

        agent_name = data["data"].get("agent_name")

        # Perform sentiment analysis
        client_sentiment = process_sentiment(client_message)
        agent_sentiment = process_sentiment(agent_message)

        # Formatting output
        result = {
            "call_id": call_id,
            "client_name": client_name,
            "call_duration": call_duration,
            "data": {
                "agent_name": agent_name,
                "agent": agent_message,
                "agent_sentiment": agent_sentiment,
                "client": client_message,
                "client_sentiment": client_sentiment,
            }
        }

        # Upload to GCS
        upload_to_gcs(result, call_id, "madrinas-bucket")
        logging.info(f"Successfully processed request for call_id: {call_id}")
        return jsonify(result), 200

    except Exception as e:
        logging.error(f"Error processing request: {e}")
        return jsonify({"error": "Failed to process request"}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=int(os.environ.get('PORT', 8080)))