function cycle(a){
	var minI = 0;
	for(var i=0; i<a.length; i++)
		if (a[i] > a[minI])
			minI = i;
	var c = a[minI];
	a[minI] = 0;
	for(var i=0; i<c; i++){
		var index = (minI + 1 + i)%a.length;
		a[index]++;		
	}
}

function solve(a){
	var i = 0;
	var s = a.toString();
	console.log(a.toString());
	var hash = {};
	var f  = false;
	while (true){
		//console.log(a.toString());
		cycle(a);
		i++;
		if (hash[a.toString()])
		{
			console.log(i);
			console.log(a.toString());
			hash = {};
			if (f) return;
			f = true;
		}
		hash[a.toString()] = 1;
	}
}

solve([5, 1, 10, 0, 1, 7, 13, 14, 3, 12, 8, 10, 7, 12, 0, 6])