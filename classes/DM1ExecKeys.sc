DM1ExecKeys {
	var <>z_down, <>keyTrackSel, <>keyTimeSel, <>clipboard;

	*new { arg keyTrackSel, keyTimeSel;
		^super.new.init(keyTrackSel,keyTimeSel); }

	init { arg keyTrackSel, keyTimeSel;
		this.z_down = false;
		this.keyTrackSel = keyTrackSel;
		this.keyTimeSel = keyTimeSel;
		this.clipboard = nil; }

	charDownHandler { arg char, score;
		if(char == $z, {z_down = true;});
		if(char == $c, {
			// copy whatever tracks selected from keyTrackSelector
			var tracks = this.keyTrackSel.getTracks;
			"tracks: ".post; tracks.postln;
			this.clipboard = Dictionary.new;
			tracks.do({|trackNum|
				this.clipboard[trackNum] = Array.newClear(score.size);
				score.size.do({|idx|
					this.clipboard[trackNum][idx] = score[idx][trackNum]; });
		}); });
		if(char == $v, {
			// paste whatever tracks saved in keyTrackSelector into the score
			// with offset given by timeSelector
			var offset = keyTimeSel.getTime;
			"offset: ".post; offset.postln;
			"clipboard: ".post; clipboard.postln;
			this.clipboard.keys.do({|key|
				"score size: ".post; score.size.postln;
				block { |break|
					score.size.do({|idx|
						// don't wrap around if z_down
						"raw index: ".post; idx.postln;
						if(((idx + offset) >= score.size).and(z_down), { break.value(1) });
						score[(idx + offset).mod(score.size)][key] = this.clipboard[key][idx];
						"current index: ".post; (idx + offset).postln;
					});
				};
			});
		});
		if(char == $k, {
			// put a velocity of 1 at the currently selected beat and track
			score[keyTimeSel.getTime][keyTrackSel.currentTrack] = 1; });
		if(char == $j, {
			// put a velocity of 0 at the currently selected beat and track
			score[keyTimeSel.getTime][keyTrackSel.currentTrack] = 1; });

		^score;
	}

	charUpHandler { arg char; if(char == $z, {z_down = false;}); }
}
					