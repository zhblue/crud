#!/bin/bash
MOVIE="$1"
START_TIME="$2"
END_TIME="$3"
OUTPUT="$4"
FFMPEG=`/usr/bin/which ffmpeg`
$FFMPEG -ss "$START_TIME" -to "$END_TIME" -accurate_seek -i "$MOVIE" -c copy "$OUTPUT" || exit 1

