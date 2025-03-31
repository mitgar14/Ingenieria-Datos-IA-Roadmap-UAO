### Exercise 1/3 
## Instalación y uso de librerías
install.packages("openxlsx")    # Carga de datos desde Excel
install.packages("dplyr")       # Manejo y transformación de datos
install.packages("ggplot2")     # Visualización de datos
install.packages("corrplot")    # Gráfico de matriz de correlaciones
install.packages("Hmisc")       # Análisis de datos (rcorr)
install.packages("mfx")         # Efectos marginales para modelos logit y probit
install.packages("gridExtra")   # Organización de gráficos
install.packages("xtable")      # Exportar tablas a LaTeX
install.packages("pROC")        # Cálculo de AUC (si es necesario)

library(openxlsx)
library(dplyr)
library(ggplot2)
library(corrplot)
library(Hmisc)
library(mfx)
library(gridExtra)
library(xtable)
library(pROC)

# Carga de datos
df_1 <- read.xlsx("Datos_MP1.xlsx")

xtable(df_1)

###############################################################################
# Análisis Exploratorio de Datos
###############################################################################

# El conjunto de datos contiene las siguientes variables:
# 
# Frec.Presente: Es el número (o frecuencia) de niños que presentan malformaciones
# en los diferentes valores ponderados de consumo de alcohol de la madre.
#
# Alcohol: Consumo de alcohol de la madre (valores ponderados: 0, 0.5, 1.5, 4,7).
#
# Frec.Ausente: Es el número (o frecuencia) de niños que NO presentan malformaciones
# en los diferentes valores ponderados de consumo de alcohol de la madre.
#
# La variable dependiente será la proporción de malformaciones, definida como:
# Proporción = Frec.Presente / (Frec.Presente + Frec.Ausente)
#
# Tabla 1: Proporción de malformaciones en niños por consumo de alcohol de la madre.
tabla_1 = df_1 %>%
  mutate(Proporcion = Frec.Presente / (Frec.Presente + Frec.Ausente)) %>%
  group_by(Alcohol) %>%
  summarise(
    Malformation_proportion_tabla = mean(Proporcion, na.rm = TRUE)
  )

tabla_1
xtable(tabla_1)

df_proporciones <- data.frame(
  Alcohol = df_1$Alcohol,
  Malformation_proportion = df_1$Frec.Presente / (df_1$Frec.Presente + df_1$Frec.Ausente)
)

df_proporciones_total <- df_1$Frec.Presente + df_1$Frec.Ausente
df_proporciones_total

# Ajuste de un modelo de regresión lineal y exportación de la tabla a LaTeX
mod_1_lineal <- lm(Malformation_proportion ~ Alcohol, data = df_proporciones)
xtable(summary(mod_1_lineal))
summary(mod_1_lineal)

mod_2_logit <- glm(Malformation_proportion ~ Alcohol, weights = df_proporciones_total, family = binomial(link = "logit"), data = df_proporciones)
xtable(summary(mod_2_logit))
summary(mod_2_logit)

mod_3_probit <- glm(Malformation_proportion ~ Alcohol, weights = df_proporciones_total, data = df_proporciones, family = binomial(link = "probit"))
xtable(summary(mod_3_probit))
summary(mod_3_probit)

# Dada la teoría previamente vista, los modelos tentativos a elegir serán el logit y el probit,
# considerando que son los más adecuados para predecir proporciones (valores entre 0 y 1).
# Se concentrará el análisis de métricas en los modelos probit y logit para elegir el mejor.

# Efecto marginal medio de probit y logit, y odds
logitmfx(formula = cbind(Frec.Presente, Frec.Ausente) ~ Alcohol, data = df_1)
probitmfx(formula = cbind(Frec.Presente, Frec.Ausente) ~ Alcohol, data = df_1)

exp(coefficients(mod_2_logit))

# pseudo-R^2 (para logit y probit)
pseudoR2_logit <- 1 - (mod_2_logit$deviance / mod_2_logit$null.deviance)
pseudoR2_logit

pseudoR2_probit <- 1 - (mod_3_probit$deviance / mod_3_probit$null.deviance)
pseudoR2_probit

# AIC, BIC, DR, RECM de los modelos logit y probit
AIC_mod_2_logit <- AIC(mod_2_logit)
BIC_mod_2_logit <- BIC(mod_2_logit)
DR_mod_2_logit <- residuals(mod_2_logit, type = "deviance")
MSE_mod_2_logit <- mean((df_proporciones$Malformation_proportion - fitted(mod_2_logit))^2)
AUC_mod_2_logit <- auc(df_proporciones$Malformation_proportion, fitted(mod_2_logit))

AIC_mod_3_probit <- AIC(mod_3_probit)
BIC_mod_3_probit <- BIC(mod_3_probit)
DR_mod_3_probit <- residuals(mod_3_probit, type = "deviance")
MSE_mod_3_probit <- mean((df_proporciones$Malformation_proportion - fitted(mod_3_probit))^2)
AUC_mod_3_probit <- auc(df_proporciones$Malformation_proportion, fitted(mod_3_probit))

#NO SE COLOCARÁ EL AUC, YA QUE TIENE UN RESULTADO PERFECTO DE AREA SOBRE LA CURVA DE 1,
# ES POSIBLEMENTE PROVOCADO PORQUE MI VARIABLE PREDICTORA NO ES 0 Y 1.

# Lista general de mediciones:
lista_mediciones_modelos <- list(
  mod_2_logit = list(
    AIC = AIC_mod_2_logit,
    BIC = BIC_mod_2_logit,
    DR = DR_mod_2_logit,
    MSE = MSE_mod_2_logit,
    pseudoR2 = pseudoR2_logit
  ),
  mod_3_probit = list(
    AIC = AIC_mod_3_probit,
    BIC = BIC_mod_3_probit,
    DR = DR_mod_3_probit,
    MSE = MSE_mod_3_probit,
    pseudoR2 = pseudoR2_probit
  )
)

tabla_mediciones_modelos <- data.frame(
  `Modelo logit` = c(
    AIC      = AIC_mod_2_logit,
    BIC      = BIC_mod_2_logit,
    DR       = mean(DR_mod_2_logit),    # Se usa la media de los residuos de devianza
    MSE     = MSE_mod_2_logit,
    pseudoR2 = pseudoR2_logit
  ),
  `Modelo probit` = c(
    AIC      = AIC_mod_3_probit,
    BIC      = BIC_mod_3_probit,
    DR       = mean(DR_mod_3_probit),
    MSE     = MSE_mod_3_probit,
    pseudoR2 = pseudoR2_probit
  )
)

tabla_mediciones_modelos
print(xtable(tabla_mediciones_modelos))

# --------------------------------------------------------------------------
# GRÁFICO DE VALORES OBSERVADOS VS PREDICHOS
# --------------------------------------------------------------------------

# Predicciones del modelo
predict_mod_1 = predict(mod_1_lineal, type = "response")
summary(predict_mod_1)

predict_mod_2 = predict(mod_2_logit, type = "response")
summary(predict_mod_2)

predict_mod_3 = predict(mod_3_probit, type = "response")
summary(predict_mod_3)

# Creamos el data frame 'df_plot' si aún no lo tienes:
df_plot <- data.frame(
  Alcohol = df_proporciones$Alcohol,
  Observado = df_proporciones$Malformation_proportion,
  Pred_lineal = predict_mod_1,
  Pred_logit = predict_mod_2,
  Pred_probit = predict_mod_3
)

# Ordenamos los datos por Alcohol (opcional, para una línea conectada ordenada)
df_plot <- df_plot[order(df_plot$Alcohol), ]

# Gráfico para el modelo lineal
p_lineal <- ggplot(df_plot, aes(x = Alcohol)) +
  geom_point(aes(y = Observado), color = "black", size = 2) +
  geom_line(aes(y = Pred_lineal), color = "blue", size = 1) +
  labs(title = "Linear Model", y = "Malformation proportion") +
  theme_minimal()

# Gráfico para el modelo logit
p_logit <- ggplot(df_plot, aes(x = Alcohol)) +
  geom_point(aes(y = Observado), color = "black", size = 2) +
  geom_line(aes(y = Pred_logit), color = "red", size = 1) +
  labs(title = "Logit model", y = "Malformation proportion") +
  theme_minimal()

# Gráfico para el modelo probit
p_probit <- ggplot(df_plot, aes(x = Alcohol)) +
  geom_point(aes(y = Observado), color = "black", size = 2) +
  geom_line(aes(y = Pred_probit), color = "green", size = 1) +
  labs(title = "Probit model", y = "Malformation proportion") +
  theme_minimal()

# Mostrar los tres gráficos juntos en una sola figura (en una fila)
png("figuras/Figura2.png")
grid.arrange(p_lineal, p_logit, p_probit, nrow = 1)
dev.off()




### -------------------------
### Exercise 2/3 

#Datos_2 - Documented by Sebastian Belalcazar to make it easy to understand
#---------------------------
#Load data
if (!require("readxl")) install.packages("readxl")
if (!require("dplyr")) install.packages("dplyr")
if (!require("ggplot2")) install.packages("ggplot2")
if (!require("MASS")) install.packages("MASS")
if (!require("pscl")) install.packages("pscl")
if (!require("pROC")) install.packages("pROC")
library(pROC)
library(readxl)
library(dplyr)
library(ggplot2)
library(MASS) #Negative binomial regression
library(pscl) #Zero-inflated model


#Load Data
datos_2 <- read_excel("C:/Users/sebas/Downloads/MP1_STATS/Datos_MP1.xlsx", sheet = 2)

#Check the class of the data
class(datos_2)
#Inspect data - first 6 rows
head(datos_2)
#Check column names
names(datos_2)
#Inspect structure
str(datos_2)

#Remove rows with NA values and verify the results
datos_2_clean <- na.omit(datos_2)
str(datos_2_clean)
head(datos_2_clean)

#NA count old and new dataset
sum(is.na(datos_2)) 
sum(is.na(datos_2_clean))


#Random data sample as requested
set.seed(123)  # For reproducibility
sample_size <- 50
datos_2_sample <- datos_2_clean[sample(nrow(datos_2_clean), sample_size), ]


#---------------------------

##EDA

#Summary statistics
summary(datos_2_sample$num_premios)

#Histogram - Distribution of Awards
hist(datos_2_sample$num_premios,
     main = 'Distribution of Number of Awards',
     xlab = 'Number of Awards',
     col = '#AFBED1',
     breaks = 10)


#Frequency table
table(datos_2_sample$programa)

#Bar plot - Distribution of Programmes
barplot(table(datos_2_sample$programa),
        main = 'Distribution of Programmes',
        xlab = 'Programmess',
        ylab = 'Frequency',
        col = '#7FBEEB')


#Summary statistics
summary(datos_2_sample$puntaje_mat)

#Histogram - Distribution of Math Scores
hist(datos_2_sample$puntaje_mat,
     main = 'Distribution of Math Scores',
     xlab = 'Math Scores',
     col = '#56CBF9',
     breaks = 10)

#Correlation analysis
cor_test <- cor.test(datos_2_sample$puntaje_mat, datos_2_sample$num_premios)
print(cor_test)


#Awards vs Programmes - Boxplot
boxplot(num_premios ~ programa,
        data = datos_2_sample,
        main = 'Number of Awards by Programme',
        xlab = 'Programme',
        ylab = 'Number of Awards',
        col = '#DBD8F0')


#Awards vs Math Score - Scatterplot
plot(datos_2_sample$puntaje_mat, datos_2_sample$num_premios,
     main = 'Number of Awards vs Math Score',
     xlab = 'Math Scores',
     ylab = 'Number of Awards',
     pch = 16,
     col = '#3D52D5')

#---------------------------

##Models

#Poisson regression model
model_poisson <- glm(num_premios ~ programa + puntaje_mat, 
                     data = datos_2_sample, 
                     family = poisson)

summary(model_poisson)


##Overdispersion test

#Residual deviance & Degrees of freedom
deviance_poisson <- deviance(model_poisson)
df_poisson <- df.residual(model_poisson)

#Chi-squared test for overdispersion
p_value_overdispersion <- pchisq(deviance_poisson, df_poisson, lower.tail = FALSE)
cat('P-value for overdispersion test:', p_value_overdispersion, "\n")

#Mean and variance
mean_awards <- mean(datos_2_sample$num_premios)
var_awards <- var(datos_2_sample$num_premios)
cat('Mean of num_premios:', mean_awards, '\n')
cat('Variance of num_premios:', var_awards, '\n')



#Exponentiate coefficients for rate ratios
rate_ratios <- exp(coef(model_poisson))
cat('Rate Ratios:\n')
print(rate_ratios)

#Confidence intervals for the coefficients
conf_intervals <- exp(confint(model_poisson))
cat('Confidence Intervals for Rate Ratios:\n')
print(conf_intervals)



#---------------------------



##Model validation

#Residual plots in a 2x2 grid (avoiding graphics device warning in RStudio -to reset the graphical parameters -> dev.off())
par(mar = c(2, 2, 2, 2))
par(mfrow = c(2, 2))
plot(model_poisson, which = 1:4)

#QQ plot for residuals - Not Necessary [https://www.geeksforgeeks.org/how-to-create-a-residual-plot-in-r/]
qqnorm(resid(model_poisson))
qqline(resid(model_poisson))


#Zero-Inflation Check
if (mean(datos_2_sample$num_premios == 0) > 0.5) {
  model_zip <- zeroinfl(num_premios ~ programa + puntaje_mat, 
                        data = datos_2_sample, 
                        dist = "poisson")
  summary(model_zip)
}


# Negative binomial model (if overdispersion is present)
if (var_awards > mean_awards * 1.5) {
  model_nb <- glm.nb(num_premios ~ programa + puntaje_mat, data = datos_2_sample)
  summary(model_nb)
  AIC(model_poisson, model_nb)
}


#Model comparison (if applicable) - [AIC - https://www.statology.org/aic-in-r/]
AIC(model_poisson, model_zip) 



#---------------------------



##Model comparison table

#RMSE - [https://www.r-bloggers.com/2021/07/how-to-calculate-root-mean-square-error-rmse-in-r/]
calculate_rmse <- function(model, data) {
  predictions <- predict(model, newdata = data, type = "response")
  actual <- data$num_premios
  sqrt(mean((predictions - actual)^2))
}


#AUC-ROC (Binary classification) - [https://www.geeksforgeeks.org/how-to-calculate-auc-area-under-curve-in-r/]
calculate_auc <- function(model, data) {
  predictions <- predict(model, newdata = data, type = "response")
  binary_actual <- ifelse(data$num_premios > 0, 1, 0)
  tryCatch({
    roc_obj <- roc(binary_actual, predictions)
    auc(roc_obj)
  }, error = function(e) {
    return(NA)  #This returns NA if ROC calculation fails
  })
}


###Pseudo-R-squared

calculate_pseudo_r2 <- function(model) {
  #log-likelihood of the model
  ll_model <- logLik(model)
  
  #log-likelihood of null model (intercept only)
  null_formula <- update(formula(model), . ~ 1)
  null_model <- update(model, formula = null_formula)
  ll_null <- logLik(null_model)
  
  #R-squared = 1 - (ll_model / ll_null)
  r2 <- 1 - (as.numeric(ll_model) / as.numeric(ll_null))
  return(r2)
}


#Metrics Poisson model
poisson_rmse <- calculate_rmse(model_poisson, datos_2_sample)
poisson_auc <- calculate_auc(model_poisson, datos_2_sample)
poisson_bic <- BIC(model_poisson)
poisson_r2 <- calculate_pseudo_r2(model_poisson)

#Metrics ZIP model
zip_rmse <- calculate_rmse(model_zip, datos_2_sample)
zip_auc <- calculate_auc(model_zip, datos_2_sample)
zip_bic <- BIC(model_zip)
zip_r2 <- calculate_pseudo_r2(model_zip)

#Negative Binomial model (only if exists)
if (exists("model_nb")) {
  nb_rmse <- calculate_rmse(model_nb, datos_2_sample)
  nb_auc <- calculate_auc(model_nb, datos_2_sample)
  nb_bic <- BIC(model_nb)
  nb_r2 <- calculate_pseudo_r2(model_nb)
  
  
  #Comparison table - all three models
  model_comparison <- data.frame(
    Model = c("Poisson", "Zero-Inflated Poisson", "Negative Binomial"),
    AIC = c(AIC(model_poisson), AIC(model_zip), AIC(model_nb)),
    BIC = c(poisson_bic, zip_bic, nb_bic),
    RMSE = c(poisson_rmse, zip_rmse, nb_rmse),
    AUC_ROC = c(poisson_auc, zip_auc, nb_auc)
  )
  
  
} else {
  #Comparison table - just two models
  model_comparison <- data.frame(
    Model = c("Poisson", "Zero-Inflated Poisson"),
    AIC = c(AIC(model_poisson), AIC(model_zip)),
    BIC = c(poisson_bic, zip_bic),
    RMSE = c(poisson_rmse, zip_rmse),
    AUC_ROC = c(poisson_auc, zip_auc),
    PseudoR2 = c(poisson_r2, zip_r2)
  )
}

#Table format
#model_comparison <- round(model_comparison[, 2:5], 4)
model_comparison <- round(model_comparison[,2:6], 4)
model_comparison <- cbind(Model = c("Poisson", "Zero-Inflated Poisson"), model_comparison)
print(model_comparison)

#Kable / Knitr -> to make the table fancier - [https://bookdown.org/yihui/rmarkdown-cookbook/kable.html]
if (require("knitr")) {
  kable_table <- knitr::kable(model_comparison, 
                              caption = "Model Comparison Metrics",
                              format = "markdown")
  print(kable_table)
}



#---------------------------



##Visualisation - Model Comparison & Predicted Values


#Calculate predicted values for both models
datos_2_sample$predicted_poisson <- predict(model_poisson, type = "response")
datos_2_sample$predicted_zip <- predict(model_zip, type = "response")

plot_data <- data.frame(
  Observation = 1:nrow(datos_2_sample),
  Observed = datos_2_sample$num_premios,
  Poisson = datos_2_sample$predicted_poisson,
  ZIP = datos_2_sample$predicted_zip
)

if (!require("reshape2")) install.packages("reshape2")
library(reshape2)
plot_data_long <- reshape2::melt(plot_data, 
                                 id.vars = c("Observation", "Observed"),
                                 variable.name = "Model", 
                                 value.name = "Predicted")

#Time series plot of predicted vs observed values
ggplot(plot_data, aes(x = Observation)) +
  geom_line(aes(y = Observed, color = "Observed"), size = 1) +
  geom_line(aes(y = Poisson, color = "Poisson"), linetype = "dashed") +
  geom_line(aes(y = ZIP, color = "ZIP"), linetype = "dotted") +
  labs(title = "Comparison of Observed and Predicted Values",
       x = "Observation Index",
       y = "Number of Awards",
       color = "Data Source") +
  scale_color_manual(values = c("Observed" = "#33A1FD", 
                                "Poisson" = "#09E85E", 
                                "ZIP" = "#E91A1A")) +
  theme_minimal()




### -------------------------
### Exercise 3/3 


#cargar librerias
library(readxl)  # Para leer archivos Excel
library(ggplot2) # Para visualización
library(MASS)    # Para regresión Binomial Negativa
library(pscl)    # Para modelos Poisson
library(ggcorrplot) # Para la matriz de correlación
library(reshape2)   # Para manipulación de datos

# Definir directorio de salida
directorio_salida <- getwd()  

# Leer los datos desde la hoja 'Datos_3'
data <- read_excel("Datos_MP1.xlsx", sheet = "Datos_3")

# Explorar la estructura de los datos
str(data)
sum(is.na(data))  # Verificar valores faltantes

# Estadísticas descriptivas
summary(data)

# Guardar estadísticas descriptivas en un archivo
sink(file.path(directorio_salida, "estadisticas_descriptivas.txt"))
print(summary(data))
sink()

# Visualización de distribuciones
p1 <- ggplot(data, aes(x=num_arrestos)) +
  geom_histogram(fill="skyblue", bins=10) +
  ggtitle("Distribución de Arrestos") +
  xlab("Número de Arrestos")
ggsave(file.path(directorio_salida, "histograma_arrestos.png"), plot = p1)

p2 <- ggplot(data, aes(x=asistencia_miles)) +
  geom_histogram(fill="lightgreen", bins=10) +
  ggtitle("Distribución de Asistencia") +
  xlab("Asistencia (miles)")
ggsave(file.path(directorio_salida, "histograma_asistencia.png"), plot = p2)

p3 <- ggplot(data, aes(x=inv_social_millones)) +
  geom_histogram(fill="orange", bins=10) +
  ggtitle("Distribución de Inversión Social") +
  xlab("Inversión (millones)")
ggsave(file.path(directorio_salida, "histograma_inversion.png"), plot = p3)

# Diagramas de bigotes
boxplot_arrestos <- ggplot(data, aes(y=num_arrestos)) +
  geom_boxplot(fill="lightblue") +
  ggtitle("Diagrama de Bigotes: Arrestos")
ggsave(file.path(directorio_salida, "boxplot_arrestos.png"), plot = boxplot_arrestos)

boxplot_asistencia <- ggplot(data, aes(y=asistencia_miles)) +
  geom_boxplot(fill="lightgreen") +
  ggtitle("Diagrama de Bigotes: Asistencia")
ggsave(file.path(directorio_salida, "boxplot_asistencia.png"), plot = boxplot_asistencia)

boxplot_inversion <- ggplot(data, aes(y=inv_social_millones)) +
  geom_boxplot(fill="orange") +
  ggtitle("Diagrama de Bigotes: Inversión Social")
ggsave(file.path(directorio_salida, "boxplot_inversion.png"), plot = boxplot_inversion)

# Gráficos de dispersión
scatter_asistencia_arrestos <- ggplot(data, aes(x=asistencia_miles, y=num_arrestos)) +
  geom_point(color="blue") +
  ggtitle("Asistencia vs Arrestos")
ggsave(file.path(directorio_salida, "scatter_asistencia_arrestos.png"), plot = scatter_asistencia_arrestos)

scatter_inversion_arrestos <- ggplot(data, aes(x=inv_social_millones, y=num_arrestos)) +
  geom_point(color="red") +
  ggtitle("Inversión Social vs Arrestos")
ggsave(file.path(directorio_salida, "scatter_inversion_arrestos.png"), plot = scatter_inversion_arrestos)

data_punto_3 <- read_excel("Datos_MP1.xlsx", sheet = "Datos_3")

data_imputando_punto_3<- data[!is.na(data$num_arrestos), ]

# Paso 2: Calcular estadísticos (mediana para asistencia, media para inversión)
mediana_asistencia <- median(data_imputando_punto_3$asistencia_miles, na.rm = TRUE)
media_inversion    <- mean(data_imputando_punto_3$inv_social_millones, na.rm = TRUE)

# Redondear la media a entero
media_inversion_redondeada <- round(media_inversion)

# Paso 3: Imputar los valores faltantes
data_imputando_punto_3$asistencia_miles[is.na(data_imputando_punto_3$asistencia_miles)] <- mediana_asistencia
data_imputando_punto_3$inv_social_millones[is.na(data_imputando_punto_3$inv_social_millones)] <- media_inversion_redondeada

# Verifica el resultado
summary(data_imputando_punto_3)

data_cleaned_punto_3 <- na.omit(data_punto_3)

#modelos

# 0.1 Modelo Lineal con datos imputados
modelo_datos_imputados_punto_3 <- lm(num_arrestos ~ asistencia_miles + inv_social_millones,
                                     data = data_imputando_punto_3)
summary(modelo_datos_imputados_punto_3)

# 0.2 Modelo Lineal con datos limpios
modelo_datos_limpios_punto_3 <- lm(num_arrestos ~ asistencia_miles + inv_social_millones,
                                   data = data_cleaned_punto_3)
summary(modelo_datos_limpios_punto_3)


# 1.1 Modelo Poisson con datos imputados
modelo_datos_imputados_punto_3_poisson <- glm(
  num_arrestos ~ asistencia_miles + inv_social_millones,
  data = data_imputando_punto_3,
  family = poisson(link = "log")  # Por defecto es link log
)

summary(modelo_datos_imputados_punto_3_poisson)

# 1.2 Modelo Poisson con datos limpios
modelo_datos_limpios_punto_3_poisson <- glm(
  num_arrestos ~ asistencia_miles + inv_social_millones,
  data = data_cleaned_punto_3,
  family = poisson(link = "log")
)

summary(modelo_datos_limpios_punto_3_poisson)


# 2.1 Modelo Binomial Negativo con datos imputados
modelo_datos_imputados_punto_3_nb <- glm.nb(
  num_arrestos ~ asistencia_miles + inv_social_millones,
  data = data_imputando_punto_3
)

summary(modelo_datos_imputados_punto_3_nb)

# 2.2 Modelo Binomial Negativo con datos limpios
modelo_datos_limpios_punto_3_nb <- glm.nb(
  num_arrestos ~ asistencia_miles + inv_social_millones,
  data = data_cleaned_punto_3
)

summary(modelo_datos_limpios_punto_3_nb)

# Metricas (con los datos limpios):

# Función para calcular el MSE
calcular_mse <- function(modelo, datos) {
  mean((datos$num_arrestos - predict(modelo, type = "response"))^2)
}

# 1. Para el modelo lineal (modelo_datos_limpios_punto_3)
metrics_lineal <- c(
  AIC       = AIC(modelo_datos_limpios_punto_3),
  BIC       = BIC(modelo_datos_limpios_punto_3),
  DR        = mean(residuals(modelo_datos_limpios_punto_3)^2),  # Residuales ordinarios
  MSE       = calcular_mse(modelo_datos_limpios_punto_3, data_cleaned_punto_3),
  pseudoR2  = summary(modelo_datos_limpios_punto_3)$r.squared      # R² del modelo lineal
)

# 2. Para el modelo Poisson (modelo_datos_limpios_punto_3_poisson)
pseudoR2_poisson <- 1 - (modelo_datos_limpios_punto_3_poisson$deviance / modelo_datos_limpios_punto_3_poisson$null.deviance)


metrics_poisson <- c(
  AIC       = AIC(modelo_datos_limpios_punto_3_poisson),
  BIC       = BIC(modelo_datos_limpios_punto_3_poisson),
  DR        = mean(residuals(modelo_datos_limpios_punto_3_poisson, type = "deviance")^2),
  MSE       = calcular_mse(modelo_datos_limpios_punto_3_poisson, data_cleaned_punto_3),
  pseudoR2  = pseudoR2_poisson
)

# 3. Para el modelo Binomial Negativo (modelo_datos_limpios_punto_3_nb)
pseudoR2_nb <- 1 - (modelo_datos_limpios_punto_3_nb$deviance / modelo_datos_limpios_punto_3_nb$null.deviance)
metrics_nb <- c(
  AIC       = AIC(modelo_datos_limpios_punto_3_nb),
  BIC       = BIC(modelo_datos_limpios_punto_3_nb),
  DR        = mean(residuals(modelo_datos_limpios_punto_3_nb, type = "deviance")^2),
  MSE       = calcular_mse(modelo_datos_limpios_punto_3_nb, data_cleaned_punto_3),
  pseudoR2  = pseudoR2_nb
)

# Crear una tabla resumen con las métricas
metrics_table <- data.frame(
  Metric             = c("AIC", "BIC", "DR", "MSE", "pseudo-R²"),
  Linear             = metrics_lineal,
  Poisson            = metrics_poisson,
  Negative_Binomial  = metrics_nb
)

# Mostrar la tabla de métricas
print(metrics_table)




# --- Generar gráficos ---

# -----------------------------
# 1. Fijar un valor representativo (por ejemplo, la media) 
#    para "inv_social_millones" en el dataset limpio
# -----------------------------
fixed_inv_cleaned <- round(mean(data_cleaned_punto_3$inv_social_millones, na.rm = TRUE))

# -----------------------------
# 2. Crear una secuencia de valores para "asistencia_miles"
#    desde su mínimo a su máximo en data_cleaned_punto_3
# -----------------------------
x_seq_cleaned <- seq(
  min(data_cleaned_punto_3$asistencia_miles, na.rm = TRUE),
  max(data_cleaned_punto_3$asistencia_miles, na.rm = TRUE),
  length.out = 100
)

# -----------------------------
# 3. Construir el data frame para predicciones:
#    variamos "asistencia_miles" y fijamos "inv_social_millones"
# -----------------------------
newdata_cleaned <- data.frame(
  asistencia_miles = x_seq_cleaned,
  inv_social_millones = fixed_inv_cleaned
)

# -----------------------------
# 4. Obtener predicciones de cada modelo
#    (type = "response" para la escala original)
# -----------------------------
pred_lm_cleaned <- predict(
  modelo_datos_limpios_punto_3,
  newdata = newdata_cleaned,
  type = "response"
)

pred_poisson_cleaned <- predict(
  modelo_datos_limpios_punto_3_poisson,
  newdata = newdata_cleaned,
  type = "response"
)

pred_nb_cleaned <- predict(
  modelo_datos_limpios_punto_3_nb,
  newdata = newdata_cleaned,
  type = "response"
)

# -----------------------------
# 5. Graficar los puntos observados vs. asistencia_miles
#    y la línea de predicción de cada modelo
# -----------------------------
par(mfrow = c(1, 3))  # Tres gráficos en una sola ventana

# Gráfico 1: Modelo lineal con datos limpios
plot(data_cleaned_punto_3$asistencia_miles, data_cleaned_punto_3$num_arrestos,
     main = "Modelo LM (limpio)",
     xlab = "Asistencia (miles)",
     ylab = "Número de arrestos",
     pch = 19, col = "blue")
lines(x_seq_cleaned, pred_lm_cleaned, col = "red", lwd = 2)

# Gráfico 2: Modelo Poisson con datos limpios
plot(data_cleaned_punto_3$asistencia_miles, data_cleaned_punto_3$num_arrestos,
     main = "Modelo Poisson (limpio)",
     xlab = "Asistencia (miles)",
     ylab = "Número de arrestos",
     pch = 19, col = "blue")
lines(x_seq_cleaned, pred_poisson_cleaned, col = "red", lwd = 2)

# Gráfico 3: Modelo Binomial Negativo con datos limpios
plot(data_cleaned_punto_3$asistencia_miles, data_cleaned_punto_3$num_arrestos,
     main = "Modelo Binomial Negativo (limpio)",
     xlab = "Asistencia (miles)",
     ylab = "Número de arrestos",
     pch = 19, col = "blue")
lines(x_seq_cleaned, pred_nb_cleaned, col = "red", lwd = 2)

# Restaurar la configuración por defecto de la ventana gráfica
par(mfrow = c(1, 1))



#------------------------
# ---------------
# ------------------------

# Calcular la media y la varianza de la variable "num_arrestos"
mean_arrests <- mean(data_cleaned_punto_3$num_arrestos, na.rm = TRUE)
var_arrests  <- var(data_cleaned_punto_3$num_arrestos, na.rm = TRUE)

# Mostrar los resultados
cat("Media de num_arrestos:", mean_arrests, "\n")
cat("Varianza de num_arrestos:", var_arrests, "\n")

# Verificar si hay sobredispersión
if (var_arrests > mean_arrests) {
  cat("La varianza es mayor que la media: se detecta sobredispersión.\n")
} else {
  cat("La varianza no es mayor que la media: no se detecta sobredispersión.\n")
}


# Modelo reajustado
modelo_nb_punto_3_reajustado <- glm.nb(
  num_arrestos ~ inv_social_millones,
  data = data_cleaned_punto_3
)

summary(modelo_nb_punto_3_reajustado)

# 3. Metricas para el modelo Binomial Negativo ajustado
pseudoR2_nb_ajustado <- 1 - (modelo_nb_punto_3_reajustado$deviance / modelo_nb_punto_3_reajustado$null.deviance)
metrics_nb_ajustado <- c(
  AIC       = AIC(modelo_nb_punto_3_reajustado),
  BIC       = BIC(modelo_nb_punto_3_reajustado),
  DR        = mean(residuals(modelo_nb_punto_3_reajustado, type = "deviance")^2),
  MSE       = calcular_mse(modelo_nb_punto_3_reajustado, data_cleaned_punto_3),
  pseudoR2  = pseudoR2_nb
)

# Crear una tabla resumen con las métricas
metrics_table_nb_ajustado <- data.frame(
  Negative_Binomial  = metrics_nb_ajustado
)

# Mostrar la tabla de métricas
print(metrics_table_nb_ajustado)

#------------------------------------------------------
#Interpretaciones del modelo ajustado
exp(coefficients(modelo_nb_punto_3_reajustado))