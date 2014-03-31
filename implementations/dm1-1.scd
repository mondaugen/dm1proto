(
s.boot;
~drum808_init.();
)

(
var loadSaveScores = DM1LoadSaveScores.new("~/Documents/scores/dm/dm-16-16-4-1.raw".standardizePath);
var score = loadSaveScores.getCurrentScore;
var keyTrackSel = DM1KeyTrackSelM.new;
var keyTimeSel = DM1KeyTimeSelASDF.new;
var execKeys = DM1ExecKeys.new(keyTrackSel,keyTimeSel,score);
var win = Window.new;
var drumMap = [6,1,8,12];
var routine = Task({
	loop {
		(0..15).do({
			|idx|
			var drums = score[idx];
			drums.do({|vel,idx| if(vel > 0,{~p808_rt.(drumMap[idx],gain:0)})});
			0.125.wait;
		});
	};
});
win.view.keyDownAction = { arg view, char, mods, unicode, keycode;
	keyTimeSel.charDownHandler(char);
	keyTrackSel.charDownHandler(char);
	score = execKeys.charDownHandler(char,score);
	if(loadSaveScores.charDownHandler(char,score),{score = loadSaveScores.getCurrentScore;});
};
win.view.keyUpAction = { arg view, char, mods, unicode, keycode;
	keyTimeSel.charUpHandler(char);
	keyTrackSel.charUpHandler(char);
	execKeys.charUpHandler(char);
};
win.view.onClose = {
	routine.stop;
	loadSaveScores.windowCloseHandler(score);
};
routine.start;
win.front;
)

