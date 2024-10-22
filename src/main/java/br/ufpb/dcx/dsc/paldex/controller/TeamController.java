package br.ufpb.dcx.dsc.paldex.controller;

import br.ufpb.dcx.dsc.paldex.DTO.TeamCreateDTO;
import br.ufpb.dcx.dsc.paldex.DTO.TeamDTO;
import br.ufpb.dcx.dsc.paldex.model.Team;
import br.ufpb.dcx.dsc.paldex.service.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@RequestBody TeamCreateDTO dto, Authentication auth) {
        Team team = teamService.createTeam(convertToEntity(dto), auth);
        return ResponseEntity.ok(convertToDTO(team));
    }

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams(Authentication auth) {
        List<Team> teams = teamService.getAllTeams(auth);
        List<TeamDTO> teamsDTO = teams.stream().map(this::convertToDTO).toList();
        return ResponseEntity.ok(teamsDTO);
    }

    @GetMapping("/{idTeam}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable("idTeam") Long idTeam, Authentication auth) {
        Team team = teamService.getTeamById(idTeam, auth);
        return ResponseEntity.ok(convertToDTO(team));
    }

    @PutMapping("/{idTeam}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable("idTeam") Long idTeam,
                                              @RequestBody TeamCreateDTO dto, Authentication auth) {
        Team updatedTeam = teamService.updateTeam(idTeam, convertToEntity(dto), auth);
        return ResponseEntity.ok(convertToDTO(updatedTeam));
    }

    @PostMapping("/{idTeam}/{idPal}")
    public ResponseEntity<TeamDTO> addPalToTeam(@PathVariable("idTeam") Long idTeam,
                                                @PathVariable("idPal") Long idPal, Authentication auth) {
        Team updatedTeam = teamService.addPalToTeam(idTeam, idPal, auth);
        return ResponseEntity.ok(convertToDTO(updatedTeam));
    }

    @DeleteMapping("/{idTeam}/{idPal}")
    public ResponseEntity<TeamDTO> removePalFromTeam(@PathVariable("idTeam") Long idTeam,
                                                     @PathVariable("idPal") Long idPal, Authentication auth) {
        Team updatedTeam = teamService.removePalFromTeam(idTeam, idPal, auth);
        return ResponseEntity.ok(convertToDTO(updatedTeam));
    }
    @DeleteMapping("/{idTeam}")
    public ResponseEntity<Void> deleteTeam(@PathVariable("idTeam") Long idTeam, Authentication auth) {
        teamService.deleteTeam(idTeam, auth);
        return ResponseEntity.noContent().build();
    }

    private TeamDTO convertToDTO(Team team) {
        return modelMapper.map(team, TeamDTO.class);
    }

    private Team convertToEntity(TeamCreateDTO dto) {
        return modelMapper.map(dto, Team.class);
    }

}
