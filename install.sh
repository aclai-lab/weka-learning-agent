#!/bin/bash

DOWNLOAD_URL="https://prdownloads.sourceforge.net/weka/weka-3-9-6-azul-zulu-linux.zip"
WEKA_DIR="weka"

if [ ! -d "$WEKA_DIR" ]; then
    echo "Downloading Weka..."
    mkdir -p "$WEKA_DIR"
    wget -O "$WEKA_DIR/weka.zip" "$DOWNLOAD_URL"
    unzip "$WEKA_DIR/weka.zip" -d "$WEKA_DIR"
    rm "$WEKA_DIR/weka.zip"
    echo "Weka downloaded successfully."
else
    echo "Weka is already installed."
fi

echo "Bye ;)"