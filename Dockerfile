FROM eclipse-temurin:25-jre-noble

ENV DISPLAY=:99

RUN apt-get update && apt-get install -y \
    xvfb \
    x11vnc \
    novnc \
    websockify \
    libgtk-3-0 \
    libxrender1 \
    libxtst6 \
    libx11-xcb1 \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY target/SustavZaUpravljanjeBolnice-1.0-SNAPSHOT.jar app.jar

EXPOSE 6080

CMD Xvfb :99 -screen 0 1400x1100x24 & \
    x11vnc -forever -shared -display :99 -nopw -listen localhost & \
    websockify --web=/usr/share/novnc/ 6080 localhost:5900 & \
    sleep 2 && \
    java -Dprism.order=sw -jar app.jar