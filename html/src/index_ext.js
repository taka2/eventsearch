Ext.onReady(function(){
    // 対象月のコンボボックス内容生成
    var monthsList = new Array();
    monthsList.push(['', '向こう三ヶ月']);

    // 6ヶ月分追加
    var currentDate = new Date();
    var strCurrnetDate = currentDate.format("Y/m");
    for(i=0; i<6; i++)
    {
       var addedDate = currentDate.add(Date.MONTH, i);
       var addedDateYyyyMm = addedDate.format("Y/m");
       monthsList.push([addedDateYyyyMm, addedDateYyyyMm]);
    }

    // 検索条件フォームの構築
    var form = new Ext.FormPanel({
        labelWidth: 90,
        frame: true,
        title: 'イベント検索条件',
        bodyStyle:'padding:5px 5px 0',
        height: 165,
        region: 'north',
        defaults: {frame: true, width: 180},
        defaultType: 'textfield',
        layout:'table',
        layoutConfig: {columns: 4},

        items: [
            new Ext.Panel({
                html: '対象月'
            })
            ,new Ext.form.ComboBox({
                fieldLabel: '対象月',
                hiddenName: 'target_month',
                store: monthsList,
                typeAhead: true,
                forceSelection: true,
                triggerAction: 'all',
                emptyText:'Select a month...',
                selectOnFocus:true,
                value: strCurrnetDate,
                editable: false
            })
            ,new Ext.Panel({
                html: '対象曜日'
            })
            , new Ext.form.ComboBox({
                fieldLabel: '対象曜日',
                hiddenName : 'target_day_of_week',
                store: [
                    ['1', '平日'],
                    ['2', '土日'],
                    ['3', '全て']
                ],
                typeAhead: true,
                forceSelection: true,
                triggerAction: 'all',
                emptyText:'Select a week...',
                selectOnFocus: true,
                value: '3',
                editable: false
            })
            ,new Ext.Panel({
                html: '時間From'
            })
            , new Ext.form.TimeField({
                fieldLabel: '時間From',
                name : 'target_time_from',
                format: 'H:i',
                increment: 30,
                value: '00:00',
                editable: false
            })
            ,new Ext.Panel({
                html: '時間To'
            })
            , new Ext.form.TimeField({
                fieldLabel: '時間To',
                name : 'target_time_to',
                format: 'H:i',
                increment: 30,
                value: '23:30',
                editable: false
            })
            ,new Ext.Panel({
                html: '住所'
            })
            ,{
                fieldLabel: '住所',
                name: 'address'
            }
            ,new Ext.Panel({
                html: 'キーワード'
            })
            ,{
                fieldLabel: 'キーワード',
                name: 'keyword'
            }
            ,new Ext.Panel({
                html: '空席あり'
            })
            , new Ext.form.Checkbox({
                fieldLabel: '空席あり',
                name : 'only_exists_vacant_seat',
                checked: true
            })
            , new Ext.Button({
                text: 'RSS出力',
                listeners: {
                    'click': function() {
                        var params = form.getForm().getValues();
                        params['output_format'] = 'rss';
                        location.href = Ext.urlEncode(params, '/search/?');
                    }
                }
            })
            , new Ext.Button({
                text: '検索',
                listeners: {
                    'click': function() {
                        var params = form.getForm().getValues();
                        params['output_format'] = 'json';

                        // 検索実施
                        Ext.Ajax.request({
                            url: "/search/",
                            method: 'GET',
                            params: params,
                            success: function(response, opts) {
                                // 検索結果を変換
                                var res = Ext.decode(response.responseText);
                                var resultList = [];
                                for(var i=0; i<res.length; i++)
                                {
                                    resultList.push([
                                        res[i]["eventSource"]
                                        , '<a href = "' + res[i]["eventUrl"] + '" target = "_blank">' + res[i]["eventId"] + '</a>'
                                        , res[i]["title"]
                                        , res[i]["startedAt"]
                                        , res[i]["endedAt"]
                                        , res[i]["place"]
                                        , res[i]["address"]
                                    ]);
                                }

                                // 検索結果をセット
                                store.loadData(resultList);

                                grid.enable();
                            }
                        });

                        grid.disable();
                    }
                }
            })
        ],
    });

    // 検索結果グリッドの構築
    var store = new Ext.data.ArrayStore({
        fields: [
           {name: 'eventSource'},
           {name: 'eventId'},
           {name: 'title'},
           {name: 'startedAt'},
           {name: 'endedAt'},
           {name: 'place'},
           {name: 'address'}
        ]
    });
    var grid = new Ext.grid.GridPanel({
        store: store,
        columns: [
            {id: 'event_id', header: 'Link', width: 40, sortable: true, dataIndex: 'eventId'},
            {id: 'event_source', header: 'ソース', width: 60, sortable: true, dataIndex: 'eventSource'},
            {id: 'title', header: 'タイトル', width: 250, sortable: true, dataIndex: 'title'},
            {id: 'startedat', header: '開始日時', width: 140, sortable: true, dataIndex: 'startedAt'},
            {id: 'endedat', header: '終了日時', width: 140, sortable: true, dataIndex: 'endedAt'},
            {id: 'place', header: '会場', width: 200, sortable: true, dataIndex: 'place'},
            {id: 'address', header: '住所', width: 200, sortable: true, dataIndex: 'address'}
        ],
        stripeRows: true,
        height: 350,
        width: 600,
        title: 'イベント検索結果',
        stateful: true,
        stateId: 'grid',
        region: 'center'
    });

    var about = new Ext.Panel({
        frame: true,
        title: 'about',
        region: 'south',
        height: 50,
        html: '<a href = "http://atnd.org/">ATND</a> / <a href = "http://www.zusaar.com/">Zusaar</a> / <a href = "http://partake.in/">PARTAKE</a>のイベントをまとめて探せるサービスです。' + 
              ' | <a href = "index_mobile.jsp">スマホ用</a>' + 
              ' | Created by <a href = "http://twitter.com/#!/taka_2" target = "_blank">@taka_2</a>'
    });

    // メインパネルの構築と描画
    var mainPanel = new Ext.Viewport({
        layout: 'border',
        items: [form, grid, about]
    });

    mainPanel.render(document.body);
});
