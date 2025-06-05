#!/bin/bash

WEKA_VERSION="weka-3-9-6"

# Detect correct OS
if [[ "$OSTYPE" == "darwin"* ]]; then
    echo -n "Running on macOS "
    if [[ "$(uname -m)" == "arm64" ]]; then
        echo "(arm64)"
        DOWNLOAD_URL=https://prdownloads.sourceforge.net/weka/${WEKA_VERSION}-azul-zulu-arm-osx.dmg
    else
        echo "(amd64)"
        DOWNLOAD_URL=https://prdownloads.sourceforge.net/weka/${WEKA_VERSION}-azul-zulu-osx.dmg
    fi
else
    echo "Running on Linux"
    DOWNLOAD_URL="https://prdownloads.sourceforge.net/weka/${WEKA_VERSION}-azul-zulu-linux.zip"
fi
WEKA_DIR="weka"

if [ ! -d "$WEKA_DIR" ] || \
   [ ! -f "$WEKA_DIR/$WEKA_VERSION/weka.jar" ] || \
   [ ! -f "$WEKA_DIR/$WEKA_VERSION/weka-src.jar" ] || \
   [ ! -f "$WEKA_DIR/$WEKA_VERSION/weka.sh" ]; then
    echo "Downloading Weka..."
    mkdir -p "$WEKA_DIR"
    curl -L -o "$WEKA_DIR/weka.zip" "$DOWNLOAD_URL"
    unzip "$WEKA_DIR/weka.zip" -d "$WEKA_DIR"
    rm "$WEKA_DIR/weka.zip"
    echo "Weka downloaded successfully."
else
    echo "Weka is already installed."
fi

echo "Bye ;)"
