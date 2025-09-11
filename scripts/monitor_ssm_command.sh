#!/bin/bash

# SSM Command Monitor Script
# Usage: ./monitor_ssm_command.sh <COMMAND_ID> <INSTANCE_ID> <AWS_REGION>

COMMAND_ID="$1"
INSTANCE_ID="$2"
AWS_REGION="$3"

if [ -z "$COMMAND_ID" ] || [ -z "$INSTANCE_ID" ] || [ -z "$AWS_REGION" ]; then
    echo "❌ Usage: $0 <COMMAND_ID> <INSTANCE_ID> <AWS_REGION>"
    exit 1
fi

echo "🔍 Monitoring SSM command: $COMMAND_ID"
echo "📍 Instance: $INSTANCE_ID"
echo "🌍 Region: $AWS_REGION"
echo ""

# Poll until command finishes
TIMEOUT=600  # 10 minutes timeout
ELAPSED=0
INTERVAL=5

while [ $ELAPSED -lt $TIMEOUT ]; do
    STATUS=$(aws ssm get-command-invocation \
        --instance-id "$INSTANCE_ID" \
        --command-id "$COMMAND_ID" \
        --region "$AWS_REGION" \
        --query 'Status' --output text 2>/dev/null)
    
    if [ $? -ne 0 ]; then
        echo "⚠️  Failed to get command status, retrying..."
        sleep $INTERVAL
        ELAPSED=$((ELAPSED + INTERVAL))
        continue
    fi
    
    echo "⏳ Status: $STATUS (${ELAPSED}s elapsed)"
    
    # Check if command reached terminal status
    case "$STATUS" in
        "Success"|"Failed"|"DeliveryTimedOut"|"ExecutionTimedOut"|"Cancelled")
            echo "🎯 Command reached terminal status: $STATUS"
            break
            ;;
    esac
    
    sleep $INTERVAL
    ELAPSED=$((ELAPSED + INTERVAL))
done

# Check for timeout
if [ $ELAPSED -ge $TIMEOUT ]; then
    echo "⏰ Timeout reached after ${TIMEOUT}s"
    exit 124
fi

# Get final results
echo ""
echo "📋 Fetching command results..."

RESPONSE_CODE=$(aws ssm get-command-invocation \
    --instance-id "$INSTANCE_ID" \
    --command-id "$COMMAND_ID" \
    --region "$AWS_REGION" \
    --query 'ResponseCode' --output text)

STDOUT=$(aws ssm get-command-invocation \
    --instance-id "$INSTANCE_ID" \
    --command-id "$COMMAND_ID" \
    --region "$AWS_REGION" \
    --query 'StandardOutputContent' --output text)

STDERR=$(aws ssm get-command-invocation \
    --instance-id "$INSTANCE_ID" \
    --command-id "$COMMAND_ID" \
    --region "$AWS_REGION" \
    --query 'StandardErrorContent' --output text)

echo ""
echo "📊 Command Results:"
echo "   Exit Code: $RESPONSE_CODE"
echo "   Status: $STATUS"
echo ""

if [ -n "$STDOUT" ] && [ "$STDOUT" != "None" ]; then
    echo "📤 STDOUT:"
    echo "----------------------------------------"
    echo "$STDOUT"
    echo "----------------------------------------"
    echo ""
fi

if [ -n "$STDERR" ] && [ "$STDERR" != "None" ]; then
    echo "🚨 STDERR:"
    echo "----------------------------------------"
    echo "$STDERR"
    echo "----------------------------------------"
    echo ""
fi

# Exit with the same code as the remote command
if [ "$RESPONSE_CODE" -eq 0 ]; then
    echo "✅ Command executed successfully"
    exit 0
else
    echo "❌ Command failed with exit code $RESPONSE_CODE"
    exit "$RESPONSE_CODE"
fi
