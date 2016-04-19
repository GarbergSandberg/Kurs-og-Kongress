/**
 * Created by eiriksandberg on 07.04.2016.
 */
sessionRegisterApp.controller('attenderInfoCtrl', ['$scope', 'attenderInfoService', 'statisticsService', '$window', function ($scope, attenderInfoService, statisticsService, $window) {
    $scope.tabs = [
        {title:'Oversikt', content:'resources/jsp/participantInfo.jsp'},
        {title:'Fakturering', content:'resources/jsp/invoice.jsp'}
    ];
    $scope.tabs.activeTab = 'Oversikt';
    $scope.registrations = [];
    $scope.selectedParticipant = {};
    $scope.showInfo = function(registration){
        if (registration !== undefined){
            self.setSessionID(registration.person.personID);
        } else {
            $window.location.href = "/kursogkongress/personInfo";
        }
    };
    $scope.showInvoice = function(){
        $window.location.href = "/kursogkongress/invoice";
    };
    $scope.showInvoiceFromList = function(registration){
        self.setSessionIDFromList(registration.person.personID);
    };

    self.resolveInfo = function() {
        var sid = sessionStorage.selectedPerson;
        if(sid !== undefined){
            attenderInfoService.getSessionStorageID(sid).then(function(success){
                $scope.selectedParticipant = self.findPerson(success);
                console.log($scope.selectedParticipant);
                $scope.selectedParticipant.attendingSessions = self.findSessions($scope.selectedParticipant);
                $scope.selectedParticipant.attendingFullCourse = self.isAttendingFullCourse($scope.selectedParticipant);
                $scope.selectedParticipant.totalAmount = self.calculateTotal($scope.selectedParticipant.cost);
                console.log($scope.selectedParticipant);
            }, function(error){
                console.log("Noe gikk galt her");
            });
        }
    };

    self.findPerson = function(id){
        for (var i = 0; i < $scope.registrations.length; i++){
            if ($scope.registrations[i].person.personID == id){
                return $scope.registrations[i];
            }
        }
    };

    self.findSessions = function (registration) {
        console.log(registration);
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

    self.getReg = function(){ // Må her ta inn cid...
        var cid = sessionStorage.selectedCourse;
        if (cid !== undefined){
            attenderInfoService.getRegistrations(cid).then(function(success){
                console.log("Length: " + success.length);
                self.mapRegistration(success);
                self.resolveInfo();
            }, function(error){
                console.log("Error");
            });
        } else console.log("sid = undefined");
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
        if(courseLength == daysAttending){
            return true;
        } else{
            return false;
        }
    };

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
        if(registration !== undefined){
            for (var i = 0; i < registration.length; i++){
                total += registration[i].amount;
            }
        }
        return total;
    };

    self.setSessionID = function(id){
        attenderInfoService.setSessionStorageID(id).then(function(successCallback){
            $window.location.href = "/kursogkongress/personInfo";
            console.log("ok. ");
        }, function(errorCallback){
            console.log("error in setSessionID");
        });
    };

    self.setSessionIDFromList = function(id){
        attenderInfoService.setSessionStorageID(id).then(function(successCallback){
            $window.location.href = "/kursogkongress/invoice";
        }, function(errorCallback){
            console.log("error in setSessionIDFromList");
        });
    };
    self.getReg();

    $scope.changeRegistration = function(){
        console.log("Setter change = true");
        $scope.change = !$scope.change;
    }
}]);