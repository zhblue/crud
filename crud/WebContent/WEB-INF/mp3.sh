#!/bin/bash
WAV="$1"
MP3="$2"
FFMPEG=`/usr/bin/which ffmpeg`
$FFMPEG -i "$WAV"  -acodec libmp3lame -y "$MP3" || exit 1
if test "$WAV" -ot "$MP3" ;then rm "$WAV";fi
