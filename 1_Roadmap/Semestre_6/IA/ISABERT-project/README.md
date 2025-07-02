<p align="center"><img src="https://readme-typing-svg.herokuapp.com?font=Time+New+Roman&color=%23FFFFFF&size=25&center=true&vCenter=true&width=1000&height=100&lines=Sentiment+Analysis+for+Five9+Calls;Whisper+/+BERT-based+Sentiment+Analysis"></a></p>

This project automates the transcription and sentiment analysis of customer service calls managed through the Five9 platform. By leveraging Whisper for speech-to-text processing, we can accurately transcribe calls and distinguish between agent and customer interactions. After transcription, a BERT-based sentiment analysis model, built with PyTorch and HuggingFace, evaluates the tone and sentiment of these conversations, providing insights that can drive better customer service decisions.


### Workflow

![Project's Workflow](https://media.licdn.com/dms/image/v2/D4E22AQEs0WSbMNxP4Q/feedshare-shrink_2048_1536/B4EZP_rkZFHEAs-/0/1735161432871?e=1754524800&v=beta&t=g7hplSUEqUlHefB2oOtbMmOE5JQ4pIFmCrzTc05NPgc)


### Prerequisites

- [Google Cloud Platform (GCP)](https://cloud.google.com/) account
- [Docker](https://www.docker.com/get-started) installed for containerization
- Python 3.8+ and necessary dependencies listed in both `requirements.txt`


### Installation

1. Clone this repository:
    ```bash
    git clone https://github.com/yourusername/five9-calls-sentiment-analysis.git
    cd five9-calls-sentiment-analysis
    ```

2. Navigate to the service directory and install dependencies:
    ```bash
    cd Sentiment-Analysis
    pip install -r requirements.txt
    ```

3. Repeat the installation steps for the `Speech-to-test` directory if needed.

>[!NOTE]
> Keep in mind that this project is designed to have both parts (STT & Sentiment Analysis) in different services. in this case everything is suite to be deploy in GCP.

### Deployment

Both the Speech-to-text and Sentiment-Analysis services are designed to be deployed on Google Cloud Platform using Cloud Run. Each service has its own `Dockerfile` to facilitate deployment. Here’s a quick guide for deploying each service:

1. **Build the Docker image**:
    ```bash
   docker build -t gcr.io/YOUR_PROJECT_ID/five9-sentiment-analysis .
    ```

2. **Deploy to Cloud Run**:

    ```bash
    gcloud run deploy five9-sentiment-analysis --image gcr.io/YOUR_PROJECT_ID/five9-sentiment-analysis --platform managed
    ```

### Usage
Once deployed, you can use the API endpoints for:

- Speech-to-text: Send audio files from Five9 to transcribe the calls, distinguishing between agent and client.

- Sentiment Analysis: Send the transcribed text for sentiment analysis to evaluate the sentiment of interactions.

### If you got a problem

1. Google it 
2. Ask an LLM
3. Let me know at lapiceroazul@proton.me
