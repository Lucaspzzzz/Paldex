package br.ufpb.dcx.dsc.paldex.service;

import br.ufpb.dcx.dsc.paldex.exception.ItemNotFoundException;
import br.ufpb.dcx.dsc.paldex.model.Pal;
import br.ufpb.dcx.dsc.paldex.model.Team;
import br.ufpb.dcx.dsc.paldex.model.User;
import br.ufpb.dcx.dsc.paldex.repository.PalRepository;
import br.ufpb.dcx.dsc.paldex.repository.TeamRepository;
import br.ufpb.dcx.dsc.paldex.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PalRepository palRepository;

    public Team createTeam(Team team, Authentication auth) {
        User currentUser = getAuthenticatedUser(auth);
        team.setUser(currentUser);
        team.setUser(userRepository.findByEmail(auth.getName()).get());
        return teamRepository.save(team);
    }

    public List<Team> getAllTeams(Authentication auth) {
        User currentUser = getAuthenticatedUser(auth);

        if (isAdmin(currentUser)) {
            return teamRepository.findAll();
        } else {
            return teamRepository.findByUserUserId(currentUser.getUserId());
        }
    }

    public Team getTeamById(Long teamId, Authentication auth) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ItemNotFoundException("Time não encontrado"));

        User currentUser = getAuthenticatedUser(auth);

        if (!team.getUser().getUserId().equals(currentUser.getUserId()) && !isAdmin(currentUser)) {
            throw new SecurityException("Você não tem permissão para acessar este time.");
        }

        return team;
    }

    public void deleteTeam(Long teamId, Authentication auth) {
        Team team = findTeamByIdAndCheckAuthorization(teamId, auth);
        teamRepository.delete(team);
    }

    public Team updateTeam(Long teamId, Team team, Authentication auth) {
        Team existingTeam = findTeamByIdAndCheckAuthorization(teamId, auth);
        existingTeam.setName(team.getName());
        return teamRepository.save(existingTeam);
    }

    @Transactional
    public Team addPalToTeam(Long teamId, Long palId, Authentication auth) {
        Team team = findTeamByIdAndCheckAuthorization(teamId, auth);
        Hibernate.initialize(team.getPals());

        Pal pal = palRepository.findById(palId)
                .orElseThrow(() -> new ItemNotFoundException("Pal não encontrado"));

        if (team.getPals().contains(pal)) {
            throw new IllegalArgumentException("Pal já está neste time!");
        }
        team.setUser(userRepository.findByEmail(auth.getName()).get());

        team.getPals().add(pal);


        return teamRepository.save(team);
    }

    @Transactional
    public Team removePalFromTeam(Long teamId, Long palId, Authentication auth) {
        Team team = findTeamByIdAndCheckAuthorization(teamId, auth);
        Hibernate.initialize(team.getPals());

        Pal pal = palRepository.findById(palId)
                .orElseThrow(() -> new ItemNotFoundException("Pal não encontrado"));

        if (!team.getPals().contains(pal)) {
            throw new ItemNotFoundException("Pal não está neste time!");
        }

        team.getPals().remove(pal);

        return teamRepository.save(team);
    }

    private Team findTeamByIdAndCheckAuthorization(Long teamId, Authentication auth) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ItemNotFoundException("Time não encontrado"));

        User currentUser = getAuthenticatedUser(auth);

        if (!team.getUser().getUserId().equals(currentUser.getUserId()) && !isAdmin(currentUser)) {
            throw new SecurityException("Você não tem permissão para acessar este time.");
        }

        return team;
    }

    private User getAuthenticatedUser(Authentication auth) {
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Usuário não encontrado"));
    }

    private boolean isAdmin(User user) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getName().name().equals("ROLE_ADMIN"));
    }

    public Set<Long> getPalIdsFromTeam(Team team) {
        return team.getPals().stream().map(Pal::getPalId).collect(Collectors.toSet());
    }
}
