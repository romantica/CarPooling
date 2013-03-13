function nextStep() {
    var fromAddress = document.getElementById("fromadd").value;
    var toAddress = document.getElementById("toadd").value;
    getGPScoord(fromAddress,
        function (fromCoordinate) {
            getGPScoord(toAddress,
                function (toCoordinate) {
                    document.getElementById("fromcoord").value = fromCoordinate.lat() + "," + fromCoordinate.lng();
                    document.getElementById("tocoord").value = toCoordinate.lat() + "," + toCoordinate.lng();
                    document.getElementById("proposalfrom").submit();
                });
        });
}
