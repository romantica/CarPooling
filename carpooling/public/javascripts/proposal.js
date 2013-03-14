function nextStep() {
    var idForm = "from_new";
    var fromAddress = document.getElementById(idForm+" fromadd").value;
    var toAddress = document.getElementById(idForm+" toadd").value;
    getGPScoord(fromAddress,
        function (fromCoordinate) {
            getGPScoord(toAddress,
                function (toCoordinate) {
                    document.getElementById(idForm+" fromcoord").value = fromCoordinate.lat() + "," + fromCoordinate.lng();
                    document.getElementById(idForm+" tocoord").value = toCoordinate.lat() + "," + toCoordinate.lng();
                    document.getElementById(idForm).submit();
                });
        });
}

