---
title: List of contributors
permalink: /devguide/contributors/
toc: false
---

<div class="contributors gridboxes">
	{% for contributor in site.data.users %}
	<div class="contributor gridbox3">
		<div class="contributor-image">
			<img src="{{ contributor.image | prepend: '/assets/images/' | relative_url }}" />
		</div>
		<div class="contributor-name">
			<h2>{{ contributor.name }}</h2>
			<p>{{ contributor.slug }}</p>
		</div>
		<div class="contributor-description">
			<p><em>{{ contributor.description }}</em></p>
		</div>
	</div>
	{% endfor %}
	{% assign contributorcount = site.data.users | size | modulo: 3 | minus: 3 | times: -1 | modulo: 3 %}
	{% for i in (1..contributorcount) %}
	<div class="contributor gridbox3"></div>
	{% endfor %}
</div>
