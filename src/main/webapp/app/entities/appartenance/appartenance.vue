<template>
  <div>
    <h2 id="page-heading" data-cy="AppartenanceHeading">
      <span v-text="$t('clarisseApp.appartenance.home.title')" id="appartenance-heading">Appartenances</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('clarisseApp.appartenance.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'AppartenanceCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-appartenance"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('clarisseApp.appartenance.home.createLabel')"> Create a new Appartenance </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && appartenances && appartenances.length === 0">
      <span v-text="$t('clarisseApp.appartenance.home.notFound')">No appartenances found</span>
    </div>
    <div class="table-responsive" v-if="appartenances && appartenances.length > 0">
      <table class="table table-striped" aria-describedby="appartenances">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.appartenance.matricule')">Matricule</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.appartenance.dateDebut')">Date Debut</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.appartenance.dateFin')">Date Fin</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.appartenance.dateElection')">Date Election</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.appartenance.observation')">Observation</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.appartenance.departement')">Departement</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.appartenance.libelle')">Libelle</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.appartenance.article')">Article</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.appartenance.motifDeDebut')">Motif De Debut</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.appartenance.commentaireDeChangement')">Commentaire De Changement</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.appartenance.typeAppartenance')">Type Appartenance</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.appartenance.structure')">Structure</span></th>
            <th scope="row"><span v-text="$t('clarisseApp.appartenance.personne')">Personne</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="appartenance in appartenances" :key="appartenance.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AppartenanceView', params: { appartenanceId: appartenance.id } }">{{
                appartenance.id
              }}</router-link>
            </td>
            <td>{{ appartenance.matricule }}</td>
            <td>{{ appartenance.dateDebut }}</td>
            <td>{{ appartenance.dateFin }}</td>
            <td>{{ appartenance.dateElection }}</td>
            <td>{{ appartenance.observation }}</td>
            <td>{{ appartenance.departement }}</td>
            <td>{{ appartenance.libelle }}</td>
            <td>{{ appartenance.article }}</td>
            <td>{{ appartenance.motifDeDebut }}</td>
            <td>{{ appartenance.commentaireDeChangement }}</td>
            <td>
              <div v-if="appartenance.typeAppartenance">
                <router-link :to="{ name: 'TypeAppartenanceView', params: { typeAppartenanceId: appartenance.typeAppartenance.id } }">{{
                  appartenance.typeAppartenance.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="appartenance.structure">
                <router-link :to="{ name: 'StructureView', params: { structureId: appartenance.structure.id } }">{{
                  appartenance.structure.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="appartenance.personne">
                <router-link :to="{ name: 'PersonneView', params: { personneId: appartenance.personne.id } }">{{
                  appartenance.personne.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AppartenanceView', params: { appartenanceId: appartenance.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AppartenanceEdit', params: { appartenanceId: appartenance.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(appartenance)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="clarisseApp.appartenance.delete.question" data-cy="appartenanceDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-appartenance-heading" v-text="$t('clarisseApp.appartenance.delete.question', { id: removeId })">
          Are you sure you want to delete this Appartenance?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-appartenance"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeAppartenance()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./appartenance.component.ts"></script>
