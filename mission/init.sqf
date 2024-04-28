fncToExecute_1 = { 
	hint format ["Extension Result 1: %1", _this];
	diag_log format ["Extension Result 1: %1", _this];
};

fncToExecute_2 = { 
	hint format ["Extension Result 2: %1", _this];
	diag_log format ["Extension Result 2: %1", _this];
};

fncToExecute_3 = { 
	hint format ["Extension Result 3: %1", _this];
	diag_log format ["Extension Result 3: %1", _this];
};

addMissionEventHandler ["ExtensionCallback",
{
	params ["_name", "_function", "_data"];

	diag_log format["ExtensionCallback - name: '%1', function: '%2', data: '%3'", _name, _function, _data];

	if (_name isEqualTo "TestExtension") then
	{
		diag_log "[TestExtension] ExtensionCallback called";
		parseSimpleArray _data call (missionNamespace getVariable [_function,
		{
			hint "Function does not exist!";
		}]);
	};

}];

"TestExtension" callExtension ["myfunc", ["abc", "123"]];
