import json
import torch
import os 
import logging

from google.cloud import storage
from transformers import AutoTokenizer, AutoModelForSequenceClassification


# Setting up logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s - %(filename)s:%(lineno)d'
)

MODEL_PATH = "/tmp/sentiment-analysis-client"

def download_model_from_gcs(bucket_name, prefix="sentiment-analysis/sentiment-analysis-client") -> None:
    
    client = storage.Client()
    bucket = client.bucket(bucket_name)

    # Descargar el modelo y el tokenizer
    blobs = bucket.list_blobs(prefix=prefix)
    os.makedirs(MODEL_PATH, exist_ok=True)
    for blob in blobs:
        file_path = os.path.join(MODEL_PATH, blob.name.split("/")[-1])
        blob.download_to_filename(file_path)
        print(f"Downloaded {blob.name} to {file_path}")

download_model_from_gcs("bucket-name")

# Load Model and Tokenizer
model = AutoModelForSequenceClassification.from_pretrained(MODEL_PATH)
tokenizer = AutoTokenizer.from_pretrained(MODEL_PATH)

# Since the model outputs 0-4, we need to map it to 1-5
def map_sentiment(value):
    return value + 1  # 0-4 -> 1-5

# Process sentiment
def process_sentiment(text):
    inputs = tokenizer(text, return_tensors="pt", truncation=True, padding="max_length", max_length=128)
    outputs = model(**inputs)
    sentiment_score = torch.argmax(outputs.logits, dim=1).item()
    return map_sentiment(sentiment_score)

def upload_to_gcs(data, call_id, bucket_name, prefix="sentiment-analysis/results"):
    client = storage.Client()
    bucket = client.bucket(bucket_name)
    
    blob = bucket.blob(f"{prefix}/{call_id}.json")
    blob.upload_from_string(json.dumps(data), content_type="application/json")
    logging.info(f"Uploaded {call_id}.json to {bucket_name}.")