# 🌐 Unit Converter Web App (Spring Boot + AJAX)

A simple, interactive **unit converter web app** built with **Spring Boot** (backend) and a clean **HTML/CSS/JS (AJAX)** frontend.  
It allows converting between **length**, **weight**, and **temperature** units — instantly and beautifully.

---

## 🚀 Features

✅ Convert between:
- **Length** → millimeter, centimeter, meter, kilometer, inch, foot, yard, mile  
- **Weight** → milligram, gram, kilogram, ounce, pound  
- **Temperature** → Celsius, Fahrenheit, Kelvin  

✅ Smooth **single-page interface** with color-themed sections  
✅ Instant conversions via **AJAX (no reload)**  
✅ Responsive & minimal modern design  
✅ Built using pure Spring Boot REST API (no database needed)

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-------------|
| Backend | Java 21 + Spring Boot |
| Frontend | HTML5, CSS3, JavaScript (jQuery) |
| Build Tool | Maven |
| Template Engine (optional) | Thymeleaf |
| API Format | JSON (via REST) |

---


## ⚙️ How It Works

1. The **frontend (`index.html`)** displays three forms:
   - Length conversion  
   - Weight conversion  
   - Temperature conversion  

2. The **JavaScript (jQuery)** listens for form submissions, sends the data as JSON via `POST` to:

/convert/length
/convert/weight
/convert/temperature

3. The **Spring Boot REST controller** (`ConversionRestController.java`) receives the request, performs conversion logic in the `Service` class, and returns JSON with:
```json
{
  "value": 100,
  "from": "kg",
  "to": "g",
  "result": 100000
}
```

4.The frontend formats and displays:

100 kg = 100,000 g

## 🧮 Example API Requests

➤ Length
```
curl -X POST http://localhost:8080/convert/length \
  -H "Content-Type: application/json" \
  -d '{"from":"m","to":"cm","value":2.5}'
```

Response:
```
{"value":2.5,"from":"m","to":"cm","result":250.0}
```

➤ Weight
```
curl -X POST http://localhost:8080/convert/weight \
  -H "Content-Type: application/json" \
  -d '{"from":"kg","to":"lb","value":5}'

```
Response:
```
{"value":5,"from":"kg","to":"lb","result":11.023}
```
➤ Temperature
```
curl -X POST http://localhost:8080/convert/temperature \
  -H "Content-Type: application/json" \
  -d '{"from":"C","to":"F","value":25}'
```

Response:
```
{"value":25,"from":"C","to":"F","result":77.0}
```

## 💻 How to Run
Step 1: Clone the repository
git clone https://github.com/yourusername/Unit-Convertor.git
cd Unit-Convertor

Step 2: Build and run
mvn spring-boot:run


or using your IDE (IntelliJ, Eclipse, VS Code).

Step 3: Open in browser
http://localhost:8080/


The app will automatically load index.html and you can start converting units instantly.

## 🎨 Frontend Preview

### Tabbed interface for Length / Weight / Temperature

### Animated color highlights on selection

### Clean result box showing only the final conversion

<img width="3420" height="1958" alt="image" src="https://github.com/user-attachments/assets/eb521b08-9697-403f-a071-c910f29f52bd" />

## 🧠 Author

### Shivam Jaiswal

💻 M.Tech (Computer Science), IIIT Bangalore

🚀 Passionate about backend systems, DevOps, and clean UI experiences.
