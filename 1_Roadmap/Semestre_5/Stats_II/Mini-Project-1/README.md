# Statistical Analysis of Health, Education, and Social Trends

![R](https://img.shields.io/badge/R-276DC3?logo=r&logoColor=white)

This repository explores three societal questions using advanced regression techniques. Below are the methodologies, key findings, and actionable insights.

---

## 🔍 Key Findings

### 1. Health Outcomes: Child Malformations and Maternal Alcohol Consumption  
**Objective**: Predict malformation proportions using alcohol consumption levels (0, 0.5, 1.5, 4, 7).  
**Models Compared**:  
- **Linear Regression**: Initially significant (β = 0.0032, p = 0.0187) but discarded due to bounded outcome limitations.  
- **Logistic Regression**:  
  - Alcohol consumption increased malformation odds by **37.24% per unit** (OR = 1.3724, p = 0.0116).  
  - Superior fit: **AIC = 24.58**, pseudo-R² = 0.6858.  
- **Probit Model**: Similar trends but weaker fit (AIC = 24.65, pseudo-R² = 0.6738).  

**Conclusion**: Logistic regression is optimal, confirming alcohol's dose-dependent risk (e.g., mothers with alcohol level 7 had **26x higher malformation odds** vs. non-consumers).

---

### 2. Education Insights: Student Awards by Program and Math Scores  
**Objective**: Model award counts (0–4) using program type (General, Academic, Vocational) and math scores.  
**Models Compared**:  
- **Poisson Regression**:  
  - Academic students had **4.15x more awards** than General (RR = 0.24, p = 0.065) and **2.18x more** than Vocational (RR = 0.46, p = 0.272).  
  - Each math score point increased awards by **6%** (RR = 1.06, p = 0.0176).  
  - Best fit: **AIC = 100.87**, no overdispersion (p = 0.575).  
- **Zero-Inflated Poisson (ZIP)**: Unstable zero-inflation component (AIC = 104.30).  

**Conclusion**: Academic programs and math proficiency drive awards. Poisson regression is preferred for parsimony and interpretability.

---

### 3. Social Impact: Soccer Match Arrests in Colombia  
**Objective**: Model arrest counts using attendance (thousands) and social investment (millions).  
**Models Compared**:  
- **Linear Regression**: Social investment reduced arrests (β = -1.53, p = 0.0035), but assumptions violated.  
- **Poisson Regression**: Overdispersed (dispersion ratio = 13.98), invalidating results.  
- **Negative Binomial Regression**:  
  - Social investment reduced arrests by **1.88% per million pesos** (IRR = 0.9812, p = 6.63e-08).  
  - Attendance was non-significant (p = 0.615).  
  - Best fit: **AIC = 161.61**, dispersion ratio = 1.02.  

**Conclusion**: Social investment is critical for reducing arrests, while attendance’s effect is negligible. Negative Binomial handles overdispersion effectively.

---

## 🛠️ Technical Highlights  
- **Model Diagnostics**: Residual plots, Cook’s distance, and Q-Q plots validated assumptions.  
- **Handling Challenges**:  
  - Zero-inflation (ZIP) and overdispersion (Negative Binomial) addressed with specialized models.  
  - Outliers retained for realism (e.g., high-attendance soccer matches).  
- **Reproducibility**: Seed-set sampling (`set.seed(123)`) and full R code provided.  