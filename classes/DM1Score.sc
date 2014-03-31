DM1Score {
	var <>score;

	*new { arg score; ^super.new.init(score); }

	init { arg score; this.score = score; }

	get { arg beat, track; ^this.score[beat][track]; }

	set { arg beat, track, value; this.score[beat][track] = value; }
}
	