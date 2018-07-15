<svg viewBox="0 0 2400 450">
{% include figure-inc.html %}

<g
		transform="translate(700,0)"
		stroke="none"
		fill="none"
		{{ text-weight-normal }}
		{{ text-size-normal }}
>

<g transform="translate(0,0)">
	<rect {{ fill-pink-01 }} x="0" y="0" width="400" height="200" rx="15" ry="15"/>
	<text {{ fill-grey-05 }} {{ bold }} {{ large }} {{ align-mm }} x="200" y="100">
		DSE Engine
	</text>
</g>

<g transform="translate(600,0)">
	<rect {{ fill-pink-01 }} x="0" y="0" width="400" height="200" rx="15" ry="15"/>
	<text {{ fill-grey-05 }} {{ bold }} {{ large }} {{ align-mm }} x="200" y="100">
		SUT
	</text>
</g>

<g transform="translate(600,250)">
	<rect {{ fill-pink-01 }} x="0" y="0" width="400" height="200" rx="15" ry="15"/>
	<text {{ fill-grey-05 }} {{ bold }} {{ large }} {{ align-mm }} x="200" y="100">
		SUT
	</text>
</g>

<g transform="translate(400,0)">
	<line x1="0"   y1="50"  x2="200" y2="50"  marker-end="url(#arrow)" {{ stroke-grey-05 }} stroke-width="10"/>
	<line x1="200" y1="100" x2="0"   y2="100" marker-end="url(#arrow)" {{ stroke-grey-05 }} stroke-width="10"/>
	<polyline stroke-linejoin="miter" marker-end="url(#arrow)"
		points="0,150 100,150 100,300 200,300"
		{{ stroke-grey-05 }} stroke-width="10"/>
	<text {{ fill-grey-05 }} {{ align-mm }} x="100" y="25">
		V1
	</text>
	<text {{ fill-grey-05 }} {{ left }} {{ align-lm }} x="115" y="225">
		V2
	</text>
</g>

</g>
</svg>
