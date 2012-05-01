Ext.define('WebConsole.MainPanel', {
  extend: 'Ext.panel.Panel',

  alias: 'widget.mainpanel',

  initComponent: function(){
    Ext.apply(this, {
      layout: 'border',
      items: [
        this.createBundleTreePanel()
        //this.createMapPanel()
      ],
      dockedItems: this.createToolbar()
    });

    this.callParent(arguments);
  },

	  createToolbar : function() {
		this.toolbar = Ext.create('widget.toolbar', {
			items : [ {
				text : 'Reload',
				handler : this.onReloadClick,
				scope : this
			}, {
				text : 'Install',
				handler : this.onBundleInstallClick,
				scope : this
			}, {
				text : 'Extensions',
				handler : this.onExtensionsClick,
				scope : this
			}, '->', {
				text : 'About',
				handler : this.onAboutClick,
				scope : this
			} ]
		});

		return this.toolbar;
	},

  createBundleTreePanel: function() {
    this.networkPanel = Ext.create('widget.bundletreepanel', {
      region: 'west',
      padding: '5 0 5 5',
      width: 350,
      listeners: {
        itemclick: this.onBundleClick,
        itemdblclick: this.onBundleDblClick,
        scope: this
      }
    });
    return this.networkPanel;
  },

  onBundleClick: function(view, record, item, index, e, eOpts) {
    var rawId = record.get('id');
    /*if (rawId) {
      var id = parseInt(rawId.substring(2));
      if (rawId.charAt(0) == 'n') {
        this.onNetworkNodeClicked(id);
      } else if (rawId.charAt(0) == 'e') {
        this.onNetworkEdgeClicked(id);
      }
    }*/
  },

  onBundleDblClick: function(view, record, item, index, e, eOpts) {
    var bundleId = record.get('id');
    if (bundleId) {
      this.readBundle(bundleId);
    }
  },

  // @param nodeId node identifier
  readBundle: function(bundleId) {

    Ext.Ajax.request({
        url: 'service/bundles/read',
        params: {
          bundleId: bundleId
        },
        success: this.onReadBundleSuccess,
        failure: this.onReadBundleFailure,
        scope: this
      });
  },

  onReadBundleSuccess: function(response) {
	  alert(response.responseText);
    var values = Ext.JSON.decode(response.responseText);
    var win = Ext.create('widget.bundleinfowindow');
    win.setFieldValues(values);
    win.show();
  },

  onReadBundleFailure: function() {
    Ext.MessageBox.show({
      title: 'Application Error',
      msg: 'There was a problem processing your request. Please try again later or contact your system administrator.',
      buttons: Ext.MessageBox.OK,
      icon: Ext.MessageBox.ERROR
    });
  },

  onReloadClick: function() {
    this.networkPanel.update();
  },

  onBundleInstallClick: function() {
    var win = Ext.create('widget.bundleinstallwindow', {
      listeners: {
        scope: this,
        bundleinstalled: this.onBundleInstalled
      }
    });

    win.show();
  },

  onBundleInstalled: function(win) {
    this.networkPanel.update();
  },


  onAddNodeClick: function() {
    var win = Ext.create('widget.nodewindow', {
        listeners: {
          scope: this,
          nodecreated: this.onNodeCreated
        }
    });

    win.show();
  },

  onNodeCreated: function(win) {
    this.networkPanel.update();
    this.mapPanel.update();
  },

  onAddEdgeClick: function() {
    // Before showing Edge Dialog fetch available nodes
    Ext.Ajax.request({
      url: 'AvailableNodes.do',
      success: this.onAddEdgeSuccess,
      failure: this.onAddEdgeFailure,
      scope: this
    });
  },

  onAddEdgeSuccess: function(response) {
    var nodes = Ext.JSON.decode(response.responseText);
    var win = Ext.create('widget.edgewindow', {
      listeners: {
        edgecreated: this.onEdgeCreated,
        scope: this
      }
    });

    win.setSource(nodes);
    win.setTarget(nodes);
    win.show();
  },

  onAddEdgeFailure: function() {
    Ext.MessageBox.show({
      title: 'Application Error',
      msg: 'There was a problem processing your request. Please try again later or contact your system administrator.',
      buttons: Ext.MessageBox.OK,
      icon: Ext.MessageBox.ERROR
    });
  },

  onEdgeCreated: function(win) {
    this.networkPanel.update();
    this.mapPanel.update();
  },

  onFrequencyDistributionClick: function() {
    var win = Ext.create('widget.paramswindow', {
        listeners: {
          scope: this,
          paramsvalid: this.onParamsValid
        }
    });

    win.show();

  },

  onParamsValid: function(paramsWin) {
    var reportWin = Ext.create('widget.reportwindow');
    reportWin.show();
  },

  onAboutClick: function() {
    var win = Ext.create('widget.aboutwindow');
    win.show();
  },
  
  onExtensionsClick : function() {
	 alert('show extensions');
  }


});