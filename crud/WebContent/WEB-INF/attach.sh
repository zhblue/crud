#!/bin/bash
FFMPEG=`/usr/bin/which ffmpeg`
$FFMPEG $*
OUTFILE=${@: -1}
TEMPFILE=`dirname $OUTFILE`"/"`basename $OUTFILE`"_$RANDOM"
mv "$OUTFILE" "$TEMPFILE"
$FFMPEG -i "$TEMPFILE" -af "volume=12dB" $OUTFILE
rm "$TEMPFILE"
