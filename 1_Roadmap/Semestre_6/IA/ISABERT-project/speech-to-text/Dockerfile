FROM python:3.11

# Allow statements and log messages to immediately appear in the Cloud Run logs
ENV PYTHONUNBUFFERED=True

RUN apt-get update && apt-get install -y libexpat1
RUN apt-get update && apt-get install -y ffmpeg

WORKDIR /app
ENV PIP_ROOT_USER_ACTION=ignore

# Update pip
RUN pip install --upgrade pip

# Install requirements
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy the source code
COPY . .
# Inform Docker that the container is listening on the specified port at runtime
EXPOSE 8080/tcp

# Start code
ENTRYPOINT ["python", "main.py"]