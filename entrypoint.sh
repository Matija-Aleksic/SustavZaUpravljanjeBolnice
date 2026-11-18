#!/bin/sh

set -e
DISPLAY_NUM=99
GEOMETRY=1400x1100x24
Xvfb ":${DISPLAY_NUM}" -screen 0 "${GEOMETRY}" &

i=0
while [ "$i" -lt 60 ]; do
    if xdpyinfo -display ":${DISPLAY_NUM}" >/dev/null 2>&1; then
        break
    fi
    i=$((i + 1))
    sleep 0.5
done

if [ "$i" -ge 60 ]; then
    echo "entrypoint: Xvfb did not come up on :${DISPLAY_NUM} within 30s" >&2
    exit 1
fi

x11vnc -forever -shared -display ":${DISPLAY_NUM}" -nopw -listen localhost -quiet &
websockify --web=/usr/share/novnc/ 6080 localhost:5900 &
exec java -XX:-UseBASE64Intrinsics -Dprism.order=sw -jar /app/app.jar
