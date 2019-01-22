if (!RedactorPlugins) var RedactorPlugins = {};

RedactorPlugins.fileupload = {
	init: function ()
	{
		this.buttonAdd('fileupload', this.opts.curLang['upload'], this.testButton);
	},
	testButton: function(buttonName, buttonDOM, buttonObj, e)
	{
		showUpload(uploadDir);
	}
};