### conda install diagrams
from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {   
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
evattr = {
    'color': 'darkgreen',
    'style': 'dotted'
}
with Diagram('smath24asynchmanycallersArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxmanycallers', graph_attr=nodeattr):
          clr1=Custom('clr1','./qakicons/symActorSmall.png')
          clr2=Custom('clr2','./qakicons/symActorSmall.png')
          callerobserver=Custom('callerobserver','./qakicons/symActorWithobjSmall.png')
     with Cluster('ctxsmath', graph_attr=nodeattr):
          smathsynch=Custom('smathsynch(ext)','./qakicons/externalQActor.png')
     clr1 >> Edge( label='startcaller', **eventedgeattr, decorate='true', fontcolor='red') >> clr2
     clr1 >> Edge(color='magenta', style='solid', decorate='true', label='<dofibo<font color="darkgreen"> fibodone</font> &nbsp; >',  fontcolor='magenta') >> smathsynch
     clr2 >> Edge(color='magenta', style='solid', decorate='true', label='<dofibo<font color="darkgreen"> fibodone</font> &nbsp; >',  fontcolor='magenta') >> smathsynch
     clr2 >> Edge(color='blue', style='solid',  decorate='true', label='<callerinfo &nbsp; >',  fontcolor='blue') >> callerobserver
     clr1 >> Edge(color='blue', style='solid',  decorate='true', label='<callerinfo &nbsp; >',  fontcolor='blue') >> callerobserver
diag
