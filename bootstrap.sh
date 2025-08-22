#!/bin/bash
set -e

echo "🚀 Running bootstrap setup..."

# Install dependencies if missing
if ! command -v aws &> /dev/null; then
  echo "Installing AWS CLI..."
  sudo apt-get update
  sudo apt-get install -y unzip curl
  curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
  unzip awscliv2.zip
  sudo ./aws/install
  rm -rf awscliv2.zip aws/
fi

if ! command -v snap &> /dev/null; then
  echo "Installing snapd..."
  sudo apt-get update
  sudo apt-get install -y snapd
fi

if ! systemctl is-active --quiet amazon-ssm-agent; then
  echo "Installing & starting SSM agent..."
  sudo snap install amazon-ssm-agent --classic
  sudo systemctl enable snap.amazon-ssm-agent.amazon-ssm-agent.service
  sudo systemctl start snap.amazon-ssm-agent.amazon-ssm-agent.service
fi

if ! command -v docker &> /dev/null; then
  echo "Installing Docker..."
  sudo apt-get update && sudo apt-get install -y ca-certificates curl gnupg lsb-release
  sudo mkdir -p /etc/apt/keyrings
  curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
  echo \
    "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
    $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
  sudo apt-get update
  sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
fi

echo "✅ Bootstrap complete"
