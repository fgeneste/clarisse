<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="typeStructure">
        <h2 class="jh-entity-heading" data-cy="typeStructureDetailsHeading">
          <span v-text="$t('clarisseApp.typeStructure.detail.title')">TypeStructure</span> {{ typeStructure.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('clarisseApp.typeStructure.codeTypeStructure')">Code Type Structure</span>
          </dt>
          <dd>
            <span>{{ typeStructure.codeTypeStructure }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.typeStructure.libelle')">Libelle</span>
          </dt>
          <dd>
            <span>{{ typeStructure.libelle }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.typeStructure.libelleCourt')">Libelle Court</span>
          </dt>
          <dd>
            <span>{{ typeStructure.libelleCourt }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.typeStructure.libellePluriel')">Libelle Pluriel</span>
          </dt>
          <dd>
            <span>{{ typeStructure.libellePluriel }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.typeStructure.urlComplete')">Url Complete</span>
          </dt>
          <dd>
            <span>{{ typeStructure.urlComplete }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.typeStructure.urlSimplifie')">Url Simplifie</span>
          </dt>
          <dd>
            <span>{{ typeStructure.urlSimplifie }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.typeStructure.ordre')">Ordre</span>
          </dt>
          <dd>
            <span>{{ typeStructure.ordre }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="typeStructure.id"
          :to="{ name: 'TypeStructureEdit', params: { typeStructureId: typeStructure.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./type-structure-details.component.ts"></script>
