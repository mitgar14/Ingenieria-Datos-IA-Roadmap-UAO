FROM python:3.12.3

# Configurar el directorio de trabajo
WORKDIR /app

# Copiar requirements.txt y descargar dependencias
COPY requirements.txt .
RUN pip install -r requirements.txt

# Copiar todos los archivos
COPY . .

# Exponer puerto para Cloud Run
EXPOSE 8080

# Ejecutar el servidor de Flask
CMD ["python", "main.py"]