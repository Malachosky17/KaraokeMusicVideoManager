<?php
# Get current time in UNIX format
$date = new DateTime();
$current_time = $date->getTimestamp();

# Place all information in one array/dictonary
$content = array('song'=> intval($_GET["song"]), 'title'=> $_GET["title"], 'artist'=> $_GET["artist"], 'author'=> $_GET["author"], 'comment'=> $_GET["comment"], 'time'=> $current_time, 'created-locally'=> false);

# Write JSON file with the timestamp as filename
$fp = fopen($current_time . '.json', 'w') or die('fopen failed');
fwrite($fp, json_encode($content)) or die('fwrite failed');
fclose($fp);

# Redirect the user to the main list
# "link-begin"
echo "<script> location.href='../html/html_party_live.html'; </script>";
# "link-end"
?>