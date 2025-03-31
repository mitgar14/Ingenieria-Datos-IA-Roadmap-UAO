import pandas as pd
import os

BASE_DATOS_RG_ALTURAS = os.environ.get("BASE_DATOS_RG_ALTURAS")
CARGA_MASIVA_2022 = os.environ.get("CARGA_MASIVA_2022")

df_base_v1 = pd.read_excel("BASE DATOS RG ALTURAS 2021 V1.xlsx", sheet_name ="GENERAL")
df_cer_b = pd.read_excel("CARGA MASIVA 2022 - Certificados B.xlsx", sheet_name="CARGA MASIVA 2022 - Certificado")

df_base_v1 = df_base_v1.loc[1770:3890,"PRIMER APELLIDO":"# DOCUMENTO"]
df_base_v1 = df_base_v1.drop(["TIPO DE DOCUMENTO"], axis=1)
df_cer_b = df_cer_b.loc[0:2117, "No documento":"Apellidos"]
df_cer_b = df_cer_b.drop(["Nombres"], axis=1)

df_base_v1["APELLIDOS"] = df_base_v1["PRIMER APELLIDO"].astype(str) + " " + df_base_v1["SEGUNDO APELLIDO"].astype(str)

df_base_v1["APELLIDOS Y CÉDULA"] = df_base_v1["APELLIDOS"].astype(str) + " " + df_base_v1["# DOCUMENTO"].astype(str)

df_cer_b["APELLIDOS Y CÉDULA"] = df_cer_b["Apellidos"].astype(str) + " " + df_cer_b["No documento"].astype(str)

lista_base_v1 = []
lista_base_cer_b = []
posibles_cedulas_confusas = []

for persona in df_base_v1["APELLIDOS Y CÉDULA"]:
    lista_base_v1.append(persona)

for persona in df_cer_b["APELLIDOS Y CÉDULA"]:
    lista_base_cer_b.append(persona)

lista_base_v1 = [x.replace("nan", "") for x in lista_base_v1]
lista_base_v1 = [x.replace("  ", " ") for x in lista_base_v1]
lista_base_v1 = [x.replace(".0", "") for x in lista_base_v1]

for persona in lista_base_v1:
    if persona in lista_base_cer_b:
        pass
    else:
        posibles_cedulas_confusas.append(persona)

for i in range(1,3):
    del posibles_cedulas_confusas[-i] #Eliminar los últimos tres elementos de las lista que no tienen sentido

print(posibles_cedulas_confusas)
print(len(posibles_cedulas_confusas))