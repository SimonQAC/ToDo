/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 85.65085646182727, "KoPercent": 14.349143538172726};
    var dataset = [
        {
            "label" : "KO",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "OK",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.5824329903331593, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.4597067692627639, 500, 1500, "\/user\/create"], "isController": false}, {"data": [0.6117643796106274, 500, 1500, "\/task\/read\/id"], "isController": false}, {"data": [0.6189207767657673, 500, 1500, "\/user\/delete"], "isController": false}, {"data": [0.5895711346732078, 500, 1500, "\/user\/read\/id"], "isController": false}, {"data": [0.5967213994419404, 500, 1500, "\/user\/update"], "isController": false}, {"data": [0.6085203563775798, 500, 1500, "\/task\/update"], "isController": false}, {"data": [0.6004637206764867, 500, 1500, "\/task\/create"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 128377, 18421, 14.349143538172726, 919.1561650451409, 0, 32040, 194.0, 7466.700000000004, 18100.95, 24629.910000000014, 1053.8685711940236, 600.4141942956533, 208.74015220313592], "isController": false}, "titles": ["Label", "#Samples", "KO", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions\/s", "Received", "Sent"], "items": [{"data": ["\/user\/create", 19234, 2889, 15.020276593532287, 1268.9161380887995, 1, 31285, 635.0, 2443.0, 2962.25, 18706.90000000056, 158.00412384684262, 96.04134513291602, 33.567866853964894], "isController": false}, {"data": ["\/task\/read\/id", 18029, 2548, 14.13278606689223, 851.7053081147006, 0, 32040, 214.0, 2004.0, 2581.5, 17543.300000000007, 148.87696118909992, 84.34016308835673, 23.511197809145337], "isController": false}, {"data": ["\/user\/delete", 17457, 2351, 13.467376983445037, 816.7827805464883, 1, 30620, 208.0, 1957.2000000000007, 2586.0, 11797.979999999967, 144.19407595856805, 67.35057930694828, 25.867927927741892], "isController": false}, {"data": ["\/user\/read\/id", 18957, 2801, 14.77554465368993, 907.8072479822732, 0, 32010, 250.0, 2091.0, 2769.0999999999985, 17450.939999999988, 156.10820603615102, 94.13996556799934, 24.466358674393707], "isController": false}, {"data": ["\/user\/update", 18636, 2730, 14.649066323245332, 889.9231594762797, 1, 30601, 233.0, 2066.0, 2712.149999999998, 17420.990000000074, 153.62674866248443, 92.26176598939055, 30.642279892133182], "isController": false}, {"data": ["\/task\/update", 17734, 2447, 13.798353445359197, 827.4282169843242, 1, 31940, 222.0, 2012.0, 2597.0, 8922.950000000033, 146.44821378433284, 82.05623381938825, 37.085754081023836], "isController": false}, {"data": ["\/task\/create", 18330, 2655, 14.484451718494272, 846.1911620294593, 0, 30261, 226.0, 2025.9000000000015, 2660.9000000000015, 13964.04999999968, 151.30878383398132, 86.84269636400782, 34.5330840927664], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["404", 6, 0.03257152163291895, 0.0046737343916745215], "isController": false}, {"data": ["Non HTTP response code: java.net.BindException\/Non HTTP response message: Address already in use: connect", 18415, 99.96742847836708, 14.34446980378105], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 128377, 18421, "Non HTTP response code: java.net.BindException\/Non HTTP response message: Address already in use: connect", 18415, "404", 6, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["\/user\/create", 19234, 2889, "Non HTTP response code: java.net.BindException\/Non HTTP response message: Address already in use: connect", 2889, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["\/task\/read\/id", 18029, 2548, "Non HTTP response code: java.net.BindException\/Non HTTP response message: Address already in use: connect", 2544, "404", 4, null, null, null, null, null, null], "isController": false}, {"data": ["\/user\/delete", 17457, 2351, "Non HTTP response code: java.net.BindException\/Non HTTP response message: Address already in use: connect", 2349, "404", 2, null, null, null, null, null, null], "isController": false}, {"data": ["\/user\/read\/id", 18957, 2801, "Non HTTP response code: java.net.BindException\/Non HTTP response message: Address already in use: connect", 2801, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["\/user\/update", 18636, 2730, "Non HTTP response code: java.net.BindException\/Non HTTP response message: Address already in use: connect", 2730, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["\/task\/update", 17734, 2447, "Non HTTP response code: java.net.BindException\/Non HTTP response message: Address already in use: connect", 2447, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["\/task\/create", 18330, 2655, "Non HTTP response code: java.net.BindException\/Non HTTP response message: Address already in use: connect", 2655, null, null, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
