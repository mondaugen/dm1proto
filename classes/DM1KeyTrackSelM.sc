DM1KeyTrackSelM {
	var <>keyTrackMap, <>keysDown, <>currentTrack;

	*new { ^super.new.init(); }

	init {
		this.keyTrackMap = Dictionary.newFrom([ $m, 0, $,, 1, $., 2, $/, 3 ]);
		this.keysDown = List.new;
		this.currentTrack = 0;
	}

	charDownHandler { arg char;
		if ([$m,$,,$.,$/].indexOf(char).notNil,{
			// only select a new track if we're not holding down many track keys at once
			if(keysDown.size == 0,{currentTrack = keyTrackMap[char]});
			if(keysDown.indexOf(char).isNil,
				{ keysDown.add(char); } );
		});
	}

	charUpHandler { arg char;
		if ([$m,$,,$.,$/].indexOf(char).notNil,
			{
			var index = keysDown.indexOf(char);
			if (index.notNil,{ keysDown.removeAt(index); });
			}
		);
	}

	getTracks { ^keysDown.collect({|item| keyTrackMap[item] }).sort; }
}