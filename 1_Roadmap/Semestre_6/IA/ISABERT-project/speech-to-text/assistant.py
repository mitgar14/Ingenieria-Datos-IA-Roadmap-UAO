import json
import logging

from openai import OpenAI
from credentials import get_secrets


class AssistantConnection:


    def __init__(self) -> None:
        self.assistant_id, self.OPENAI_API_KEY = get_secrets()
        self.five9calls = OpenAI(api_key=self.OPENAI_API_KEY)
        self.thread = self.five9calls.beta.threads.create()


    def extract_values_from_messages(self, messages: json) -> str:
        """
        Extract the values from the GPT output
        Args:
            messages (json): Messages from the assistant
        Returns:
            str: Extracted values from the messages, False otherwise
        """
        try:
            values = []
            for message in messages.data:
                for content_block in message.content:
                    if content_block.type == 'text':
                        values.append(content_block.text.value)
            return "\n".join(values)
        except Exception as e:
            logging.error(f"Error extracting values from messages: {e}")
            return False


    def send_message(self, transcribed_text: str, training: bool) -> str:
        """
        Send a message to the GPT assistant
        Args:
            transcribed_text (str): Transcribed text from the call
        Returns:
            str: Extracted values from the messages, False otherwise
        """

        # Instructions with sentiment analysis
        inst_training = f"""
            Vas a recibir una transcripción de una llamada entre un cliente y un agente de una empresa llamada "Las Madrinas" que vende seguros de Obamacare. Tu tarea consiste en identificar y clasificar claramente qué fragmento de texto corresponde al cliente y cuál al agente, logrando que ambos parezcan diálogos naturales.

                1. *Clasificación de los roles*:
                - Identifica quién está hablando en cada parte de la transcripción, asignando las partes de manera precisa al client o al agent.
                - Omite detalles irrelevantes para el análisis, como nombres, correos electrónicos, números de teléfono, direcciones, o muletillas. Puedes también reducir las stopwords según sea necesario para agilizar el mensaje.
                
                2. *Identificar nombre del agente*:
                - Si el agente menciona su nombre, asegúrate de incluirlo en la respuesta dentro de la llave "agent_name".
                - Estos son los nombres de nuestros agentes: ['Islen Tellez', 'Ariagna Bejarano', 'Liz Renda', 'Nelly Mosqueira Merino', 'Sergio Pérez', 'Fernando Pérez']
                - Si el agente no menciona su nombre, déjalo en blanco.

                3. *Naturalidad en el lenguaje*:
                - Procura que las respuestas sean claras y suenen naturales, como si fueran transcripciones realistas. No generalices ni uses términos vagos; mantén el contexto específico de la conversación.
                - Modera el texto para mantener su esencia pero eliminando el ruido innecesario.
                
                4. *Preparación para análisis de sentimiento*:
                - Realiza una lematización y, si es necesario, un proceso ligero de stemming en el texto, para que los mensajes queden claros y enfocados en la intención original.
                - *Criterios para el análisis de sentimiento*:
                    - *1 (Muy negativo)*: El cliente o agente expresa frustración, quejas intensas, o insatisfacción significativa.
                    - *2 (Negativo)*: El cliente o agente muestra descontento o inquietudes leves, sin llegar a un tono extremo.
                    - *3 (Neutral)*: No se detecta emoción específica; el tono es informativo o simplemente explicativo.
                    - *4 (Positivo)*: El cliente o agente expresa satisfacción o interés leve sin entusiasmo exagerado.
                    - *5 (Muy positivo)*: El cliente o agente muestra entusiasmo, agradecimiento o una actitud claramente favorable.
                    
                5. *Respuesta formateada en JSON*:
                - Resume los mensajes desde la perspectiva de cada rol (cliente y agente) en primera persona y asegúrate de que el mensaje de cada uno no exceda las 400 palabras.
                - La respuesta debe incluir un análisis de sentimiento, representado como un número entero entre 1 y 5, según los criterios indicados anteriormente.

                Ejemplo de formato JSON para la respuesta, en caso de no poder hacer la clasificación retornar el JSON vacío:

                ```json
                {{
                    "client": "client-message",
                    "client_sentiment": 1 to 5,
                    "agent_name": "Liz Renda",
                    "agent": "agent-message",
                    "agent_sentiment": 1 to 5
                }} 
                
                Aquí tienes la transcripción de la llamada:
                {transcribed_text}        

            """

        # Instructions without sentiment analysis
        inst_production = f"""
            Vas a recibir una transcripción de una llamada entre un cliente y un agente de una empresa llamada "Las Madrinas" que vende seguros de Obamacare. Tu tarea consiste en identificar y clasificar claramente qué fragmento de texto corresponde al cliente y cuál al agente, logrando que ambos parezcan diálogos naturales.

                1. *Clasificación de los roles*:
                - Identifica quién está hablando en cada parte de la transcripción, asignando las partes de manera precisa al client o al agent.
                - Omite detalles irrelevantes para el análisis, como nombres, correos electrónicos, números de teléfono, direcciones, o muletillas. Puedes también reducir las stopwords según sea necesario para agilizar el mensaje.
                
                2. *Identificar nombre del agente*:
                - Si el agente menciona su nombre, asegúrate de incluirlo en la respuesta dentro de la llave "agent_name".
                - Estos son los nombres de nuestros agentes: ['Islen Tellez', 'Ariagna Bejarano', 'Liz Renda', 'Nelly Mosqueira Merino', 'Sergio Pérez', 'Fernando Pérez']
                - Si el agente no menciona su nombre, déjalo en blanco.

                3. *Naturalidad en el lenguaje*:
                - Procura que las respuestas sean claras y suenen naturales, como si fueran transcripciones realistas. No generalices ni uses términos vagos; mantén el contexto específico de la conversación.
                - Modera el texto para mantener su esencia pero eliminando el ruido innecesario.
                
                4. *Respuesta formateada en JSON*:
                - Resume los mensajes desde la perspectiva de cada rol (cliente y agente) en primera persona y asegúrate de que el mensaje de cada uno no exceda las 400 palabras.

                Ejemplo de formato JSON para la respuesta, en caso de no poder hacer la clasificación retornar el JSON vacío:

                ```json
                {{
                    "client": "client-message",
                    "agent_name": "Liz Renda",
                    "agent": "agent-message"
                }} 
                
                Aquí tienes la transcripción de la llamada:
                {transcribed_text}        

            """

        try:
            if training == True:
                inst = inst_training
            else:
                inst = inst_production
            
            # Create & run the thread
            run = self.five9calls.beta.threads.runs.create_and_poll(
                thread_id=self.thread.id,
                assistant_id=self.assistant_id,
                instructions=inst
            )

            if run.status == 'completed':
                messages = self.five9calls.beta.threads.messages.list(thread_id=self.thread.id)
                response = self.extract_values_from_messages(messages)
                return response
            else:
                return run.status
        except Exception as e:
            logging.error(f"Error sending message to GPT: {e}")
            return False
    
    