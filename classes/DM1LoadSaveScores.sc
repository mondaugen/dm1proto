DM1LoadSaveScores {
	// scores are saved in a 16 x 16 x 4 x 1 byte format
	var <>filename, <>savedScores, <>savedScoreIdx;

	*new { arg filename;
		^super.new.init(filename); }

	init { arg filename, savedScores, savedScoreIdx;
		this.filename = filename;
		this.savedScores = File.use(filename,"rb",{
			|file|
			var result = Array.fill(16,{Array.fill(16,{Array.fill(4,{0})})});
			block { |break|
				16.do({|i|
					16.do({|j|
						4.do({|k|
							var byte;
							byte = file.getInt8;
							if(byte.isNil,{break.value(69)},{result[i][j][k] = byte});
						});
					});
				});
			};
			result
		});
		this.savedScoreIdx = 0;
	}

	getCurrentScore { ^savedScores[savedScoreIdx].deepCopy; }

	charDownHandler { arg char, score;
		// returns true if it changed the current score so the calling function knows now to
		// load it using getCurrentScore
		if (Set[$-,$=].includes(char),{
			savedScores[savedScoreIdx] = score;
			if(char == $-,{savedScoreIdx = (savedScoreIdx - 1).wrap(0,15)});
			if(char == $=,{savedScoreIdx = (savedScoreIdx + 1).wrap(0,15)});
			savedScoreIdx.postln;
			^true;
		}, { ^false }); }

	windowCloseHandler { arg score;
		savedScores[savedScoreIdx] = score;
		File.use(filename.standardizePath,"wb",{
			|file|
			block { |break|
				16.do({|i|
					16.do({|j|
						4.do({|k|
							file.putInt8(savedScores[i][j][k]);
						});
					});
				});
			};
		});
	}

}