function songLaden(pfad) {
    var player = document.getElementById('player');
    var mp3Source = document.getElementById('player');
    mp3Source.src = pfad;
    
   player.load();
   player.play();
}