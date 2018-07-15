<svg viewBox="0 0 2400 680">
{% include figure-inc.html %}

<g
		transform="translate(600,0)"
		stroke="none"
		fill="none"
		{{ text-weight-normal }}
		{{ text-size-normal }}
>

<g transform="translate(0,80)">
	<rect {{ fill-pink-02 }} x="0" y="0" width="500" height="600" rx="15" ry="15"/>
	<text {{ fill-grey-05 }} {{ bold }} {{ large }} {{ align-mm }} x="250" y="-30">
		DSE / symbolic
	</text>
<!-- state 1 -->
	<rect {{ fill-pink-03 }}
			{{ stroke-pink-04 }}
			stroke-width="2"
			x="50" y="50"
			width="400" height="100"
			rx="5" ry="5"/>
	<text {{ fill-grey-05 }} {{ align-mm }} x="250" y="100">
		a = A
	</text>
<!-- assign 1 -->
	<text {{ fill-grey-05 }} {{ align-mm }} x="250" y="225">
		a := a + 1
	</text>
<!-- state 2 -->
	<rect {{ fill-pink-03 }}
			{{ stroke-pink-04 }}
			stroke-width="2"
			x="50" y="300"
			width="400" height="100"
			rx="5" ry="5"/>
	<text {{ fill-grey-05 }} {{ align-mm }} x="250" y="350">
		a = A+1
	</text>
<!-- if 1 -->
	<text {{ fill-grey-05 }} {{ align-mm }} x="250" y="475">
		A+1 &gt; 4
	</text>
</g>

<g transform="translate(700,80)">
	<rect {{ fill-pink-02 }} x="0" y="0" width="500" height="600" rx="15" ry="15"/>
	<text {{ fill-grey-05 }} {{ bold }} {{ large }} {{ align-mm }} x="250" y="-30">
		SUT / concrete
	</text>
	<rect {{ fill-pink-03 }}
			{{ stroke-pink-04 }}
			stroke-width="2"
			x="50" y="50"
			width="400" height="100"
			rx="5" ry="5"/>
	<text {{ fill-grey-05 }} {{ align-mm }} x="250" y="100">
		a = 5
	</text>
	<text {{ fill-grey-05 }} {{ align-mm }} x="250" y="225">
		a := a + 1
	</text>
	<rect {{ fill-pink-03 }}
			{{ stroke-pink-04 }}
			stroke-width="2"
			x="50" y="300"
			width="400" height="100"
			rx="5" ry="5"/>
	<text {{ fill-grey-05 }} {{ align-mm }} x="250" y="350">
		a = 6
	</text>
	<text {{ fill-grey-05 }} {{ align-mm }} x="250" y="475">
		IF a > 4...
	</text>
</g>

<g transform="translate(600,80)">
	<line x1="200" y1="225" x2="-200" y2="225"
			marker-end="url(#arrow)"
			{{ stroke-grey-05 }}
			stroke-width="8"/>
	<line x1="200" y1="475" x2="-200" y2="475"
			marker-end="url(#arrow)"
			{{ stroke-grey-05 }}
			stroke-width="8"/>
</g>

</g>
</svg>
