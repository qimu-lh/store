const MAX = 40

const cp = new CircleProgress01('.progress01', {
    max: MAX,
    value: parseInt(Math.random() * 40),//生成随机温度
    animationDuration: 400,
    textFormat: (val) => val + '℃',
});
cp.el.style.setProperty('--progress01-value', cp.value / MAX);
$("#temperature").text(cp.value);//将生成随机温度赋值给temperature


/*document.querySelector('#value-input-Tem').addEventListener('change', e => {
	const val = e.target.value;
	cp.value = val;
	cp.el.style.setProperty('--progress01-value', val / MAX);
})*/

