var render = function (theme, data, meta, require) {
    theme('index', {
        title: [
			{ partial:'title', context: data.title}
		],
		nav: [
			{ partial:'nav', context: data.nav}
		],
		body: [
			{ partial:'body', context: data.body}
		]
    });
};

