#!/bin/bash

# Detect correct OS
if [[ "$OSTYPE" == "darwin"* ]]; then
    echo -n "Running on macOS "
    if [[ "$(uname -m)" == "arm64" ]]; then
        DOWNLOAD_URL=https://prdownloads.sourceforge.net/weka/weka-3-9-6-azul-zulu-arm-osx.dmg
        echo "(arm64)"
    else
        echo "(amd64)"
        DOWNLOAD_URL=https://prdownloads.sourceforge.net/weka/weka-3-9-6-azul-zulu-osx.dmg
    fi
else
    echo "Running on Linux"
    DOWNLOAD_URL="https://prdownloads.sourceforge.net/weka/weka-3-9-6-azul-zulu-linux.zip"
fi
WEKA_DIR="weka"

if [ ! -d "$WEKA_DIR" ]; then
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
