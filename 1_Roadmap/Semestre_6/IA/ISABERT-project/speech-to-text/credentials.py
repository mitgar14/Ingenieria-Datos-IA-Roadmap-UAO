import json

from google.cloud import secretmanager


def get_secrets() -> tuple:
    """
    Get the OpenAI API Key and Assistant ID from Google Secret Manager
    Returns:
        tuple: OpenAI API Key and Assistant ID
    """
    client = secretmanager.SecretManagerServiceClient()
    name = f"projects/whateverproject/secrets/whatever-secret/versions/latest"
    response = client.access_secret_version(request={"name": name})
    secret_string = response.payload.data.decode("UTF-8")
    secrets = json.loads(secret_string)
    return secrets["Assistant_Id"], secrets["OPENAI_API_KEY"]