/**
 * Created by eiriksandberg on 21.04.2016.
 */
topMenyApp.controller('addNewCourseCtrl', function($scope){
    $scope.resetSessionStorage = function(){
        if (sessionStorage.cid.exists){
            console.log("CID eksisterer. Sletter");
            sessionStorage.removeItem("cid");
        } else{
            console.log("cid eksisterer ikke");
        }
    }
});