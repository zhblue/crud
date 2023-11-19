#!/bin/bash
MOVIE="$1"
START_TIME="$2"
VOICE="$3"
OUTPUT="$4"
FFMPEG=`/usr/bin/which ffmpeg`
$FFMPEG -i "$MOVIE" -itsoffset "$START_TIME" -i "$VOICE" -filter_complex amix=inputs=2:duration=first:dropout_transition=4 -async 1 -c:v copy "$OUTPUT"