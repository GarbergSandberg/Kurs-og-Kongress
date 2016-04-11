/**
 * Created by eiriksandberg on 07.04.2016.
 */
attenderInfoApp.controller('attenderInfoCtrl', ['$scope', 'attenderInfoService', '$window', function ($scope, attenderInfoService, $window) {
    $scope.tabs = [
        {title:'Oversikt', content:'resources/jsp/participantInfo.jsp'},
        {title:'Fakturering', content:'resources/jsp/invoice.jsp'}
    ];
    $scope.tabs.activeTab = 'Oversikt';
    $scope.registrations = [];
    $scope.selectedParticipant = {};
    $scope.showInfo = function(registration){
        console.log(registration + "    " + registration.person.personID);
        sessionStorage.selectedPerson = registration.person.personID;
        /*attenderInfoService.setSessionStorageID(name.personID);*/
        $window.location.href = "/kursogkongress/personInfo";
    };
    $scope.showInvoice = function(){
        $window.location.href = "/kursogkongress/invoice";
    };

    $scope.showInvoiceFromList = function(registration){
        sessionStorage.selectedPerson = registration.person.personID;
        $window.location.href = "/kursogkongress/invoice";
    };



    self.resolveInfo = function() {
        var sid = sessionStorage.selectedPerson;
        console.log("SID: " + sid);
        if (sid !== undefined) {
            $scope.selectedParticipant = self.findPerson(sid);
            $scope.selectedParticipant.attendingSessions = self.findSessions($scope.selectedParticipant);
            $scope.selectedParticipant.attendingFullCourse = self.isAttendingFullCourse($scope.selectedParticipant);
            $scope.selectedParticipant.totalAmount = self.calculateTotal($scope.selectedParticipant.cost);
        }


/*        var sid = sessionStorage.selectedPerson;
        console.log("SID: " + sid);
        if(sid !== undefined){
            console.log("Er inne her");
            attenderInfoService.getSessionStorageID(sessionStorage.selectedPerson).then(function(success){
                console.log(success);
                $scope.selectedParticipant = self.findPerson(id);
            }, function(error){
                console.log("Noe gikk galt her");
            });
        }*/
    };

    self.findPerson = function(id){
        for (var i = 0; i < $scope.registrations.length; i++){
            if ($scope.registrations[i].person.personID == id){
                return $scope.registrations[i];
            }
        }
    };

    self.findSessions = function (registration) {
        var sessionArray = [];
        for (var i = 0; i < registration.sessionsToAttend.length; i++){
            var u = registration.sessionsToAttend[i];
            for (var x = 0; x < registration.course.sessions.length; x++){
                if (u == registration.course.sessions[x].id){
                    sessionArray.push(registration.course.sessions[x]);
                    break;
                }
            }
        }
        console.log(sessionArray);
        return sessionArray;
    };

    self.getReg = function(){
        attenderInfoService.getRegistrations(0).then(function(success){
            console.log("Length: " + success.length);
            self.mapRegistration(success);
            self.resolveInfo();
        }, function(error){
        });
    };

    self.mapRegistration = function(registrations){
        for (var i = 0; i < registrations.length; i++){
            var registration = registrations[i];
            if (registration != null){
                $scope.registrations.push(registration);
            }
        }
    };

    self.isAttendingFullCourse = function(registration){
        var courseLength = self.getDates(new Date(registration.course.startDate), new Date(registration.course.endDate)).length;
        var daysAttending = registration.dates.length;
        console.log(courseLength + "    " + daysAttending);
        if(courseLength == daysAttending){
            return true;
        } else{
            return false;
        }
    }

    Date.prototype.addDays = function(days) {
        var dat = new Date(this.valueOf());
        dat.setDate(dat.getDate() + days);
        return dat;
    };

    self.getDates = function(startDate, stopDate) {
        var dateArray = new Array();
        var currentDate = startDate;
        while (currentDate <= stopDate) {
            dateArray.push(currentDate);
            currentDate = currentDate.addDays(1);
        }
        return dateArray;
    };

    self.calculateTotal = function(registration){
        var total = 0;
        console.log("selectedparticipant.cost = " + registration);
        if(registration !== undefined){
            for (var i = 0; i < registration.length; i++){
                total += registration[i].amount;
            }
        }
        return total;
    };

    self.getReg();
}]);