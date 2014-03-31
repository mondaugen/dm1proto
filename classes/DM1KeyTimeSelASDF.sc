DM1KeyTimeSelASDF {
	var <>keyTimeMap, <>keysDown;

	*new { ^super.new.init(); }

	init {
		this.keyTimeMap = Dictionary.newFrom(
			[
				"a",   0,
				"ad",  2,
				"ads", 1,
				"adf", 3,
				"s",   4,
				"sd",  6,
				"sda", 5,
				"sdf", 7,
				"d",   8,
				"df",  10,
				"dfa", 9,
				"dfs", 11,
				"f",   12,
				"fs",  14,
				"fsa", 13,
				"fsd", 15
			]
		);
		this.keysDown = List.new;
	}

	charDownHandler { arg char;
		if ([$a,$s,$d,$f].indexOf(char).notNil.and(keysDown.indexOf(char).isNil),
			{ keysDown.add(char); }
		);
	}

	charUpHandler { arg char;
		if ([$a,$s,$d,$f].indexOf(char).notNil,
			{
			var index = keysDown.indexOf(char);
			if (index.notNil,{ keysDown.removeAt(index); });
			}
		);
	}

	getTime { ^keyTimeMap[keysDown.inject("",{|x,y| x++y})]; }
}