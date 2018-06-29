<svg viewBox="0 0 2400 300">
{% include figure-inc.html %}

<g
		transform="translate(700,0)"
		stroke="none"
		fill="none"
		{{ text-weight-normal }}
		{{ text-size-normal }}
>

<g>
	<rect {{ fill-blue-01 }} x="0" y="0" width="400" height="300" rx="15" ry="15"/>
	<text {{ fill-grey-05 }} {{ align-lt }} x="20" y="50">
		JVM1
	</text>
	<text {{ fill-grey-05 }} {{ bold }} {{ large }} {{ align-mm }} x="200" y="150">
		DEEPSEA
	</text>
</g>

<g transform="translate(600,0)">
	<rect {{ fill-blue-01 }} x="0" y="0" width="400" height="300" rx="15" ry="15"/>
	<text {{ fill-grey-05 }} {{ align-lt }} x="20" y="50">
		JVM2
	</text>
	<text {{ fill-grey-05 }} {{ bold }} {{ large }} {{ align-mm }} x="200" y="150">
		SUT
	</text>
</g>

<g transform="translate(400,0)">
	<line x1="0"   y1="100" x2="200" y2="100" marker-end="url(#arrow)" {{ stroke-grey-05 }} stroke-width="10"/>
	<line x1="200" y1="200" x2="0"   y2="200" marker-end="url(#arrow)" {{ stroke-grey-05 }} stroke-width="10"/>
</g>

</g>
</svg>
