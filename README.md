# Finsmart Core 🚀

**Finsmart**, bireysel ve kurumsal kullanıcılar için tasarlanmış, yapay zekâ (Gemini AI) destekli kurumsal düzeyde bir finansal yönetim ve analiz platformudur.

## 🏗️ Mimari Kararımız: Mikroservislerden Modüler Monolite Geçiş

Bu proje başlangıçta 5 bağımsız servisten oluşan bir mikroservis mimarisi olarak tasarlandı. Ancak, artan DevOps eforları, deployment karmaşıklığı ve over-engineering (gereğinden fazla mühendislik) maliyetlerini düşürmek adına, mikroservislerin disiplinini (Bounded Contexts) koruyarak **Modüler Monolit (Modular Monolith)** mimarisine geçiş yapma yönünde stratejik bir karar aldık.

Bu sayede elde ettiklerimiz:
- **Teknoloji Sadeliği:** Tek bir Spring Boot JVM'i ile daha az donanım maliyeti.
- **Operasyonel Rahatlık:** CI/CD süreçlerinin çok daha hızlı ve hatasız çalışması.
- **Sınırların Korunması:** Kod seviyesinde `auth`, `finance`, `analysis` ve `ai` modüllerinin kesin sınırlarla birbirinden ayrılması.

## 🛠️ Teknoloji Yığını (Tech Stack)

- **Backend:** Java 21, Spring Boot 3.x
- **Veritabanı:** PostgreSQL (Relational Database)
- **Mesajlaşma & Eventing:** Apache Kafka (Transactional Outbox Pattern kullanılmaktadır)
- **Güvenlik:** Spring Security, JWT (JSON Web Token), Refresh Token Rotation
- **Yapay Zekâ:** Gemini API
- **Containerization:** Docker & Docker Compose

## 📐 Temel Mühendislik Kuralları (Finsmart Core Guidelines)

Uygulama geliştirilirken aşağıdaki kıdemli optimizasyon kurallarına sadık kalınır:
1. **N+1 Problemine Son:** İlişkisel yapılar varsayılan olarak `FetchType.LAZY` yüklenir. Gerektiğinde `JOIN FETCH` veya `EntityGraph` ile optimize edilir.
2. **Kritik İndeksleme:** Veritabanında sık sorgulanan alanlarda (örn: `account_id`, `transaction_date`) Composite Index'ler mevcuttur.
3. **DTO Projeksiyonları:** Sadece ihtiyaç duyulan veri seçilerek gereksiz RAM kullanımının önüne geçilir.
4. **Veri Tutarlılığı:** Asenkron iletişimde veri kaybı yaşanmaması için Event'ler doğrudan fırlatılmaz, **Transactional Outbox** tablosuyla veritabanı ile aynı Transaction içinde kaydedilir.
5. **Erken Dönüş (Early Return):** Hata durumlarında iç içe `if` blokları yerine özel exception'lar fırlatılarak temiz kod yazılır.

## 🚀 Başlangıç (Getting Started)

Projeyi ayağa kaldırmak için:

```bash
# Gerekli altyapıyı (PostgreSQL vb.) ayağa kaldır
docker-compose up -d

# Projeyi çalıştır
./mvnw spring-boot:run
```

## 📂 Modül Yapısı
* `com.finsmart.core.auth` -> Kimlik doğrulama ve JWT işlemleri.
* `com.finsmart.core.finance` -> Gelir/gider fatura yönetimi ve hesap işlemleri.
* `com.finsmart.core.analysis` -> Opsiyonel - Analitik hesaplamalar ve tetikleyiciler.
* `com.finsmart.core.ai` -> Opsiyonel - Gemini destekli finansal akıl hocası.
