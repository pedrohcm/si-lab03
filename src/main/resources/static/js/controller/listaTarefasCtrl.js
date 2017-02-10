angular.module("listaTarefas", ["ngMessages"]);
angular.module("listaTarefas").controller("listaTarefasCtrl", function ($scope, $http) {

    $scope.app = "Lista Tarefas";
    $scope.listaAtual = {nome:"Agenda de Tarefas", tarefas:[]};

    $scope.tarefas = null;

    $scope.listas = [];


    $scope.prioridades = ["BAIXA", "MEDIA", "ALTA"];

    $scope.alteraListaAtual = function (novaLista) {
        $scope.listaAtual = novaLista;
        $scope.tarefas = novaLista.tarefas;
        $scope.listaSelecionada = true;
    };

    $scope.alterarTerminada = function(tarefa) {
        if(tarefa.terminada) {
            tarefa.terminada = false;
        } else {
            tarefa.terminada = true;
        }
        $scope.atualizarTarefa(tarefa);

    };

    $scope.atualizarTarefasDoBD = function() {
            $http({method:'GET', url:'http://localhost:8080/listas/' + $scope.listaAtual.id})
                .then(function(response){
                    $scope.tarefas = response.data;
                    console.log(response.status);
                }, function(response){
                    console.log(response.data);
                    console.log(response.status);
                })
        };

    $scope.tarefaTerminada = function (tarefa) {
        return tarefa.terminada;
    };

    $scope.atualizarTarefa = function(tarefa) {
        $http({method:'PUT', url:'http://localhost:8080/listas/tarefa', data:tarefa})
            .then(function(response){
                console.log(response.status);
            }, function(response){
                console.log(response.data);
                console.log(response.status);
            })
    };

    $http({method:'GET', url:'http://localhost:8080/listas'})
        .then(function(response){

            $scope.listas = response.data;
            console.log(response.data);

        }, function(response){
            console.log(response.data);
            console.log(response.status);
        });

    $scope.listaAdicionada = [];

    $scope.adicionarTarefa = function (tarefa) {
        tarefa.terminada = false;
        $scope.salvarTarefa(tarefa);
        $scope.apagarLeitor(tarefa);
    };

    $scope.apagarLeitor = function(tarefa) {
        delete $scope.tarefa;
    };

    $scope.salvarLista = function () {
        if($scope.listaAtual.id == null) {
            $scope.adicionarNovaLista($scope.listaAtual);
        }
    };

    $scope.salvarTarefa = function(tarefa) {
        $http({method:'POST', url:'http://localhost:8080/listas/' + $scope.listaAtual.id, data:tarefa})
            .then(function(response){

                $scope.tarefas.push(response.data);
                console.log(response.data);

                $scope.fechaAdicionaTarefa();

                if ($scope.categorias.indexOf(response.data.categoria) == -1) {
                    $scope.categorias.push(response.data.categoria);
                }

            }, function(response){
                console.log(response.data);
                console.log(response.status);
            })
    };

    $scope.apagarTarefas = function (tarefa) {
        $scope.tarefas = tarefas.filter(function (tarefa) {
            if (!tarefa.completa) return tarefa;
        });
    };

    $scope.classe = 'completa';

    $scope.temTarefaCompleta = function (tarefas) {
        return tarefas.some(function (tarefa) {
            return tarefa.completa;
        });
    };

    $scope.limpaLista = function () {
        $scope.listaAtual.tarefas = [];
        $scope.tarefas = [];
        $scope.deletarTodasTarefas($scope.listaAtual);
    };

    $scope.deletarTodasTarefas = function(listaDeTarefas) {
        $http({method:'DELETE', url:'http://localhost:8080/listas/tarefas/' + listaDeTarefas.id})
            .then(function(response){

                console.log(response.status);

            }, function(response){
                console.log(response.data);
                console.log(response.status);
            })
    }

    $scope.temTarefas = function () {
        return $scope.tarefas.length > 0;
    };

    $scope.adicionarNovaLista = function(listaTarefas) {
        $http({method:'POST', url:'http://localhost:8080/listas', data: listaTarefas})
            .then(function(response){

                $scope.apagarLeitor(listaTarefas);
                $scope.listaAtual = response.data;
                $scope.listas.push(response.data);
                $scope.tarefas = response.data.tarefas;

                console.log(response.status);
                console.log(response.data);

            }, function(response){
                console.log(response.data);
                console.log(response.status);
            })
    };

    $scope.deletarTodasAsListas = function() {
        $http({method:'DELETE', url:'http://localhost:8080/listas'})
            .then(function(response){

                $scope.listas = [];
                $scope.listaAtual = {nome:"Agenda de Tarefas", tarefas:[]};
                $scope.tarefaSelecionada = false;

                console.log(response.status);

            }, function(response){
                console.log(response.data);
                console.log(response.status);
            })
    };

    $scope.deletarTarefa = function (tarefa) {
        var indiceLista = $scope.listaAtual.tarefas.indexOf(tarefa);
        $scope.listaAtual.tarefas.splice(indiceLista);
        $scope.deletaTarefaBD(tarefa);
    }

    $scope.deletaTarefaBD = function(tarefa) {
        $http({method:'DELETE', url:'http://localhost:8080/listas/' + $scope.listaAtual.id + "/" + tarefa.id})
            .then(function(response){

                console.log(response.status);

            }, function(response){
                console.log(response.data);
                console.log(response.status);
            })
    }


    $scope.temListas = function () {
        return $scope.listas.length > 0;
    }

    $scope.porcentagemConcluida = function (tarefas) {
        if(!$scope.temTarefas()) {
            return 0;
        }
        var porcentagem = ($scope.quantidadeConcluidas() * 100) / $scope.tarefas.length;
        return parseInt(porcentagem);
    };

    $scope.quantidadeTarefasListaAtual = function () {
        return $scope.listaAtual.tarefas.length;
    };

    $scope.quantidadeConcluidas = function () {
        var terminadas = 0;
        for (var i = 0; i < $scope.tarefas.length; i++) {
            if ($scope.listaAtual.tarefas[i].terminada) terminadas++;
        }
        return terminadas;
    };

    $scope.calculaPorcentagem = function (tarefas) {

        var sum = 0;

        tarefas.forEach(function (tarefa) {

            if(tarefa.concluida) {
                sum += 1;
            }
        });

        var porcent = 0;

        if (tarefas.length > 0) {
            porcent = (sum/tarefas.length) * 100;
        }

        return Math.floor(porcent);
    }

    // Get the modal
    var modalAdicionaTarefa = document.getElementById('modalAdicionaTarefa');

// Get the button that opens the modal
    var botaoAdicionarTarefa = document.getElementById('botaoTarefa');
    var botaoDetalharTarefa = document.getElementById('abrirDetalhes');

// Get the <span> element that closes the modal
    //var span = document.getElementsByClassName("close")[0];

    var botaoFechar = document.getElementById("botaoFechar");

// When the user clicks on the button, open the modal
    botaoAdicionarTarefa.onclick = function() {
        modalAdicionaTarefa.style.display = "block";
    }



// When the user clicks on <span> (x), close the modal
    botaoFechar.onclick = function() {
        modalAdicionaTarefa.style.display = "none";
    }

    $scope.fechaAdicionaTarefa = function () {
        modalAdicionaTarefa.style.display = "none";
    }

    $scope.tarefaSelecionada = null;

    $scope.abrirDetalhes = function (tarefa) {
        modalDetalhesTarefa.style.display = "block";
        $scope.tarefaSelecionada = tarefa;
    }

    $scope.fechaDetalhes = function () {
        modalDetalhesTarefa.style.display = "none";
        $scope.editaTarefa = false;
    }

// When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == modalAdicionaTarefa) {
            modalAdicionaTarefa.style.display = "none";
        }
    }

    window.onclick = function(event) {
        if (event.target == modalDetalhesTarefaTarefa) {
            modalDetalhesTarefaTarefa.style.display = "none";
        }
    };



});
